package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ApporteurDAO {

    public static Stream<ApporteurEntity> getAll() {
        return HibernateUtils.getSession()
                .createQuery("from Apporteur ", ApporteurEntity.class)
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

    public static ApporteurEntity insertApporteur(String nom, String prenom, int parrain) {
        Session session = HibernateUtils.getSession();
        Transaction transaction = null;
        ApporteurEntity apporteur = null;

        ApporteurEntity _parrain = null;
        /*if (parrain != -1)
            _parrain = ApporteurDAO.getById((long) parrain);*/

        try {
            transaction = session.beginTransaction();
            apporteur = new ApporteurEntity();
            apporteur.setNom(nom);
            apporteur.setPrenom(prenom);
            apporteur.setParrain(_parrain);
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
}
