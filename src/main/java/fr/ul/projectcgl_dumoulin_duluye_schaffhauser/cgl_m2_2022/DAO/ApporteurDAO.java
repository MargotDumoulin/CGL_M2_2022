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
        LocalDate compDate = LocalDate.from(LocalDate.now()).minusMonths(3);
        String hqlQuery = "" +
                "SELECT apporteur.nom " +
                "FROM Apporteur AS apporteur, Affaire AS affaire " +
                "WHERE apporteur.id = affaire.apporteur.id " +
                "AND apporteur.id = :apporteurId " +
                "AND affaire.date >= :comparativeDate";

        Stream<String> resStream = super.getSession()
                .createQuery(hqlQuery, String.class)
                .setParameter("apporteurId", apporteurId)
                .setParameter("comparativeDate", Date.from(compDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .getResultStream();


        return resStream.map(Optional::ofNullable).findFirst().flatMap(Function.identity()).isPresent();
    }
}
