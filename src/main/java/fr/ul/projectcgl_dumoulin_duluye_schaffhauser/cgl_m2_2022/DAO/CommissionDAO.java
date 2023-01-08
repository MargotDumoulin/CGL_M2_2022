package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.stream.Stream;

public class CommissionDAO {

    public static Stream<Double> getTotalByMonthAndApporteurId(int month, int year, Long apporteurId) {
         String hqlQuery = "" +
                "SELECT SUM(commission.montant) " +
                "FROM Commission AS commission, Affaire AS affaire " +
                "WHERE commission.id.affaire.id  = affaire.id " +
                "AND affaire.apporteur.id = :apporteurId " +
                "AND MONTH(affaire.date) = :month " +
                "AND YEAR(affaire.date) = :year";

        // String hqlQuery = "SELECT commission.montant FROM Commission AS commission ";

        return HibernateUtils.getSession()
                .createQuery(hqlQuery, Double.class)
                .setParameter("month", month)
                .setParameter("year", year)
                .setParameter("apporteurId", apporteurId)
                .getResultStream();
    }
}