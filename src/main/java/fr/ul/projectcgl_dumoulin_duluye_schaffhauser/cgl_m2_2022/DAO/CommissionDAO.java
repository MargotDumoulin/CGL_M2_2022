package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.CommissionPerso;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;

import java.util.stream.Stream;

public class CommissionDAO {

    private static CommissionDAO instance;

    public static CommissionDAO getInstance() {
        if (instance == null) {
            instance = new CommissionDAO();
        }

        return instance;
    }

    private CommissionDAO() {
    }

    public Stream<Double> getTotalByMonthAndApporteurId(int month, int year, Long apporteurId) {
         String hqlQuery = """
                SELECT SUM(commission.montant)
                FROM Commission AS commission, Affaire AS affaire
                WHERE commission.id.affaire.id  = affaire.id
                  AND commission.id.apporteur.id = :apporteurId
                  AND MONTH(affaire.date) = :month
                  AND YEAR(affaire.date) = :year
                  """;

        return HibernateUtils.getInstance().getSession()
                .createQuery(hqlQuery, Double.class)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("apporteurId", apporteurId)
                .getResultStream();
    }

    public Stream<CommissionPerso> getCommissionsByAffaire(Long affaireId) {
        String hqlQuery = """
                SELECT commission
                FROM Commission AS commission, Affaire AS affaire
                WHERE commission.id.affaire.id  = :affaireId
                """;

        return HibernateUtils.getInstance().getSession()
                .createQuery(hqlQuery, CommissionPerso.class)
                .setParameter("affaireId", affaireId)
                .getResultStream();
    }
}