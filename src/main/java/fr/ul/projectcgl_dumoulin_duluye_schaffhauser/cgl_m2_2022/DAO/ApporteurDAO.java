package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public boolean deleteApporteur(ApporteurEntity apporteur) {
        try (Session session = super.getSession()) {
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

                session.remove(apporteur);

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

        return isPresent(apporteur.getId());
    }
}
