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

    public Boolean getIsAffilie(Long apporteurId) {
        Long durationSetting = Long.parseLong(SettingsDAO.getInstance().getByCode("DUREE_MIN_AFFILIE").getValeur());
        System.out.println(durationSetting);
        Long minAffairesSetting = Long.parseLong(SettingsDAO.getInstance().getByCode("NB_MIN_AFFAIRES").getValeur());
        System.out.println(minAffairesSetting);
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

        System.out.println(nbOfAffaires);

        return nbOfAffaires >= minAffairesSetting;
    }
}
