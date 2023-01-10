package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.stream.Stream;

public class ApporteurDAO {

    public static Stream<ApporteurEntity> getAll() {
        return HibernateUtils.getInstance().getSession()
                .createQuery("from Apporteur ", ApporteurEntity.class)
                .getResultStream();
    }

    public static ApporteurEntity getById(Long id) {
        try (Session session = HibernateUtils.getInstance().getSession()) {
            return session.get(ApporteurEntity.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isPresentApporteur(Long id) {
        return Optional.ofNullable(getById(id)).isPresent();
    }

    public static ApporteurEntity insertApporteur(ApporteurEntity apporteur) {
        try (Session session = HibernateUtils.getInstance().getSession()) {
            session.save(apporteur);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return apporteur;
    }

    public static ApporteurEntity updateApporteur(ApporteurEntity apporteur) {
        try (Session session = HibernateUtils.getInstance().getSession()) {
            session.update(apporteur);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return getById(apporteur.getId());
    }

    public static boolean deleteApporteur(ApporteurEntity apporteur) {
        try (Session session = HibernateUtils.getInstance().getSession()) {
            Transaction tx = session.beginTransaction();
            String query = """
                    UPDATE Apporteur
                    SET parrain = null
                    WHERE parrain.id = :parrainId
                    """;
            try {
                session.createQuery(query)
                        .setParameter("parrainId", apporteur.getId())
                        .executeUpdate();

                session.delete(apporteur);

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                tx.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return isPresentApporteur(apporteur.getId());
    }
}
