package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import com.oracle.wls.shaded.org.apache.xpath.operations.Bool;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntityId;
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

            this.insertCommissions(affaire, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }
        }  finally {
            session.close();
        }

        return affaire;
    }

    public void insertCommissions(AffaireEntity affaire, Session session) {
        // Insert commission for apporteur
        Double globalCom = affaire.getCommissionGlobale();
        this.insertCommission(affaire, affaire.getApporteur(), session, globalCom - (globalCom * 0.05)); // PRENDRE POURCENTAGE DEPUIS PARAMS

        // Insert commission for first parrain
        ApporteurEntity parr = affaire.getApporteur().getParrain();
        Double parrainCom = globalCom * 0.05;
        this.insertCommission(affaire, parr, session, parrainCom);

        // Insert commissions pour les parrains d'après...
        parr = parr.getParrain();
        parrainCom = parrainCom / 2; // PRENDRE POURCENTAGE DEPUIS PARAMS

        while (parr != null) {
            this.insertCommission(affaire, parr, session, parrainCom);
            parr = parr.getParrain();
            parrainCom = parrainCom / 2;
        }
    }

    public void insertCommission(AffaireEntity affaire, ApporteurEntity apporteur, Session session, Double montant) {
        CommissionEntityId comEntId = new CommissionEntityId(affaire, apporteur);
        CommissionEntity com = new CommissionEntity(comEntId, montant);
        session.merge(com);
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


}
