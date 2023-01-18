package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.stream.Stream;

public class AffaireDAO extends AbstractDAO<AffaireEntity, Long> {
    private static AffaireDAO instance;

    public static AffaireDAO getInstance() {
        if (instance == null) {
            instance = new AffaireDAO();
        }

        return instance;
    }

    private AffaireDAO() {
        super(AffaireEntity.class);
    }

    public AffaireEntity insertOrUpdate(AffaireEntity affaire, Boolean isUpdate) {
        Session session = HibernateUtils.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (isUpdate) {
                session.merge(affaire);
            } else {
                session.persist(affaire);
            }

            CommissionDAO.getInstance().insertCommissions(affaire, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        } finally {
            session.close();
        }

        return affaire;
    }

    public Stream<AffaireEntity> getAllDirByAppId(int pageSize, int start, Long apporteurId, String orderBy, String dir, Boolean filterByCurrMonth) {
        String query = "SELECT affaire FROM Affaire AS affaire WHERE affaire.apporteur.id = :apporteurId";
        if (filterByCurrMonth) query += " AND MONTH(affaire.date) = " + LocalDate.now().getMonthValue();
        query += " ORDER BY " + orderBy + " " + dir;

        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setParameter("apporteurId", apporteurId)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultStream();
    }

    public Stream<AffaireEntity> getAllDirByAppId(Long apporteurId) {
        String query = "SELECT affaire FROM Affaire AS affaire WHERE affaire.apporteur.id = :apporteurId";
        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setParameter("apporteurId", apporteurId)
                .getResultStream();
    }

    public Stream<AffaireEntity> getAllByAppId(int pageSize, int start, Long apporteurId, String orderBy, String dir, Boolean filterByCurrMonth) {
        String query = """
                SELECT affaire 
                FROM Affaire AS affaire, Commission AS commission 
                WHERE commission.id.apporteur.id = :apporteurId 
                AND commission.id.affaire.id = affaire.id
                """;

        if (filterByCurrMonth) query += " AND MONTH(affaire.date) = " + LocalDate.now().getMonthValue();
        query += " ORDER BY " + orderBy + " " + dir;

        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setParameter("apporteurId", apporteurId)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultStream();
    }

    public Stream<AffaireEntity> getAllByAppId(Long apporteurId) {
        String query = """
                SELECT affaire 
                FROM Affaire AS affaire, Commission AS commission 
                WHERE commission.id.apporteur.id = :apporteurId 
                AND commission.id.affaire.id = affaire.id
                """;
        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setParameter("apporteurId", apporteurId)
                .getResultStream();
    }

    public Stream<AffaireEntity> getAll(int pageSize, int start, String orderBy, String dir, Boolean filterByCurrMonth) {
        String query = "from Affaire AS affaire";
        if (filterByCurrMonth) query += " WHERE MONTH(affaire.date) = " + LocalDate.now().getMonthValue();
        query += " ORDER BY " + orderBy + " " + dir;

        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultStream();
    }

    @Override
    public boolean delete(AffaireEntity entity) {
        String query = """
                DELETE FROM Commission
                WHERE id.affaire.id = :affaireId
                """;

        try (Session session = getSession()) {
            Transaction tx = session.beginTransaction();

            try {
                session.createQuery(query, null)
                        .setParameter("affaireId", entity.getId())
                        .executeUpdate();

                session.remove(entity);

                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
/*            throw e;*/
        }

        return isPresent(entity.getId());
    }
    @Override
    public boolean delete(Long id) {
        return delete(getById(id));
    }
}
