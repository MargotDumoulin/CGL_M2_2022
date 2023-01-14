package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ApporteurDAO extends AbstractDAO<ApporteurEntity, Long> {

    private static ApporteurDAO instance;

    public static ApporteurDAO getInstance() {
        if (instance == null) {
            instance = new ApporteurDAO();
        }

        return instance;
    }

    private ApporteurDAO() {
        super(ApporteurEntity.class);
    }

    public Stream<ApporteurEntity> getAll(int pageSize, int start) {
        LocalDate currentDate = LocalDate.now();
        LocalDate m1Date = LocalDate.from(currentDate).minusMonths(1);
        LocalDate m2Date = LocalDate.from(currentDate).minusMonths(2);

        String sqlQuery = """
                SELECT t_apporteur.*
                FROM apporteur AS t_apporteur
                LEFT JOIN (SELECT t_commission.*
                           FROM commission t_commission
                           LEFT JOIN affaire AS t_affaire ON t_commission.AFFAIRE_ID = t_affaire.ID
                           WHERE YEAR(t_affaire.DATE) = :year
                             AND MONTH(t_affaire.DATE) = :month) AS c ON c.APPORTEUR_ID = t_apporteur.ID
                GROUP BY t_apporteur.id
                ORDER BY COALESCE(SUM(c.MONTANT), 0) DESC
                """;

        return getSession()
                .createNativeQuery(sqlQuery, ApporteurEntity.class)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .setParameter("month", currentDate.getMonthValue())
                .setParameter("year", currentDate.getYear())
                .getResultStream();
    }

    public Boolean getIsAffilie(Long apporteurId) {
        Long durationSetting = Long.parseLong(SettingsDAO.getInstance().getByCode("DUREE_MIN_AFFILIE").getValeur());
        Long minAffairesSetting = Long.parseLong(SettingsDAO.getInstance().getByCode("NB_MIN_AFFAIRES").getValeur());
        LocalDate compDate = LocalDate.from(LocalDate.now()).minusMonths(durationSetting);

        String hqlQuery = "" +
                "SELECT COUNT(apporteur) " +
                "FROM Apporteur AS apporteur, Affaire AS affaire " +
                "WHERE apporteur.id = affaire.apporteur.id " +
                "AND apporteur.id = :apporteurId " +
                "AND affaire.date >= :comparativeDate";

        Long nbOfAffaires = getSession()
                .createQuery(hqlQuery, Long.class)
                .setParameter("apporteurId", apporteurId)
                .setParameter("comparativeDate", Date.from(compDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .getSingleResult();
        
        return nbOfAffaires >= minAffairesSetting;
    }
}
