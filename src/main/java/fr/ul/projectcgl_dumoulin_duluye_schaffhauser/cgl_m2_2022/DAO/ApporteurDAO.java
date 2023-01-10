package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ApporteurDAO {

    public static Stream<ApporteurEntity> getAll() {
        return HibernateUtils.getSession()
                .createQuery("from Apporteur ", ApporteurEntity.class)
                .getResultStream();
    }

    public static Stream<ApporteurEntity> getAll(int pageSize, int start) {
        return HibernateUtils.getSession()
                .createQuery("from Apporteur ", ApporteurEntity.class)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultStream();
    }

    public static ApporteurEntity getById(Long id) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ApporteurEntity entity = session.get(ApporteurEntity.class, id);
            tx.commit();

            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public static ApporteurEntity insertApporteur(String nom, String prenom, ApporteurEntity parrain) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = null;
        ApporteurEntity apporteur = null;

        try {
            transaction = session.beginTransaction();
            apporteur = new ApporteurEntity();
            apporteur.setNom(nom);
            apporteur.setPrenom(prenom);
            apporteur.setParrain(parrain);
            session.save(apporteur);

            transaction.commit();

            return apporteur;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
    }

    public static Boolean getIsAffilie(Long apporteurId) {
        LocalDate compDate = LocalDate.from(LocalDate.now()).minusMonths(3);
        String hqlQuery = "" +
                "SELECT apporteur.nom " +
                "FROM Apporteur AS apporteur, Affaire AS affaire " +
                "WHERE apporteur.id = affaire.apporteur.id " +
                "AND apporteur.id = :apporteurId " +
                "AND affaire.date >= :comparativeDate";

        Stream<String> resStream = HibernateUtils.getSession()
                .createQuery(hqlQuery, String.class)
                .setParameter("apporteurId", apporteurId)
                .setParameter("comparativeDate", Date.from(compDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .getResultStream();


        return resStream.map(Optional::ofNullable).findFirst().flatMap(Function.identity()).isPresent();
    }
}
