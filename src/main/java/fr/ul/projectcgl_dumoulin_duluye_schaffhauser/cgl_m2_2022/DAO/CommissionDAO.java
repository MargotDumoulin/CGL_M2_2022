package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntityId;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.CommissionPerso;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;

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

    public Stream<CommissionEntity> getAll() {
        String query = "from Commission ";
        return HibernateUtils.getInstance().getSession()
                .createQuery(query, CommissionEntity.class)
                .getResultStream();
    }

    public void insertCommissions(AffaireEntity affaire, Session session) {
        Double globalCom = affaire.getCommissionGlobale();
        Double totalComParrain = 0.0;
        Double dirParrPercent = Double.parseDouble(SettingsDAO.getInstance().getByCode("DIR_PARR_VALUE").getValeur());
        Double indirParrPercent = Double.parseDouble(SettingsDAO.getInstance().getByCode("INDIR_PARR_VALUE").getValeur());
        ApporteurEntity parr = affaire.getApporteur().getParrain();

        if (parr != null) {
            // Insert commission for first parrain
            Double parrainCom = ApporteurDAO.getInstance().getIsAffilie(parr.getId()) ? globalCom * dirParrPercent : 0;
            totalComParrain += parrainCom;
            this.insertCommission(affaire, parr, session, parrainCom);

            // Insert commissions pour les parrains d'après...
            parr = parr.getParrain();
            while (parr != null) {
                parrainCom = ApporteurDAO.getInstance().getIsAffilie(parr.getId()) ? parrainCom * indirParrPercent : 0;
                totalComParrain += parrainCom;
                this.insertCommission(affaire, parr, session, parrainCom);

                parr = parr.getParrain();
            }
        }

        // Insert commission for apporteur
        this.insertCommission(affaire, affaire.getApporteur(), session, globalCom - totalComParrain);
    }

    public void insertCommission(AffaireEntity affaire, ApporteurEntity apporteur, Session session, Double montant) {
        CommissionEntityId comEntId = new CommissionEntityId(affaire, apporteur);
        CommissionEntity com = new CommissionEntity(comEntId, montant);
        session.merge(com);
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
                SELECT DISTINCT commission
                FROM Commission AS commission, Affaire AS affaire
                WHERE commission.id.affaire.id  = :affaireId
                """;

        return HibernateUtils.getInstance().getSession()
                .createQuery(hqlQuery, CommissionPerso.class)
                .setParameter("affaireId", affaireId)
                .getResultStream();
    }
}