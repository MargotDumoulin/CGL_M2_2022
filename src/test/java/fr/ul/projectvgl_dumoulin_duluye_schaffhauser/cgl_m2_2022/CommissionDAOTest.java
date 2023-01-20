package fr.ul.projectvgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.AffaireDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.CommissionDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntityId;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommissionDAOTest {

    private static final long ID_AFFAIRE_TO_GET = 1;
    private static final long ID_APPORTEUR_TO_GET_1 = 1;
    private static final long ID_APPORTEUR_TO_GET_2 = 2;
    private static final long ID_APPORTEUR_TO_GET_3 = 3;

    @BeforeAll
    public static void initTest() {
        Session session = HibernateUtils.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        String insertApporteurQuery = """
                INSERT INTO apporteur (`ID`, `PRENOM`, `NOM`, `PARRAIN_ID`) VALUES
                (1, 'Antony', 'DUDU', NULL),
                (2, 'Margot', 'DUMOULING', 1),
                (3, 'Bastieng', 'CHAT FAOU ZER', 1),
                (4, 'Daddy', 'DADDY GOOD', NULL),
                (5, 'Mustang', 'COQ', 4),
                (6, 'Loic', 'VERSET', 4),
                (7, 'Tutur', 'TROGNON', 4);
                """;
        String insertAffaireQuery = """
                INSERT INTO affaire (`ID`, `APPORTEUR_ID`, `DATE`, `COMMISSION_GLOBALE`) VALUES
                (1, 1, '2023-01-11', 200),
                (2, 1, '2023-01-11', 200),
                (3, 2, '2022-12-14', 200),
                (4, 2, '2022-12-14', 200),
                (5, 2, '2022-11-16', 200);
                """;
        String insertCommissionsQuery = """
                INSERT INTO commission (`AFFAIRE_ID`, `APPORTEUR_ID`, `MONTANT`) VALUES
                (1, 1, 1),
                (1, 2, 2),
                (1, 3, 3),
                (2, 1, 2),
                (2, 3, 1),
                (3, 1, 3),
                (3, 2, 1),
                (3, 3, 2);
                """;

        session.createNativeQuery("DELETE FROM commission", CommissionEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM affaire", AffaireEntity.class).executeUpdate();
        session.createNativeQuery("UPDATE apporteur SET PARRAIN_ID = NULL", ApporteurEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM apporteur", ApporteurEntity.class).executeUpdate();

        session.createNativeQuery(insertApporteurQuery, ApporteurEntity.class).executeUpdate();
        session.createNativeQuery(insertAffaireQuery, AffaireEntity.class).executeUpdate();
        session.createNativeQuery(insertCommissionsQuery, CommissionEntity.class).executeUpdate();

        tx.commit();
    }

    @Test
    public void getCommissionById1() {
        CommissionEntity commission;

        commission = CommissionDAO.getInstance().getById(
                CommissionEntityId.builder()
                        .apporteur(ApporteurDAO.getInstance().getById(ID_APPORTEUR_TO_GET_1))
                        .affaire(AffaireDAO.getInstance().getById(ID_AFFAIRE_TO_GET))
                        .build());

        assertThat(commission)
                .isNotNull()
                .returns(1D, CommissionEntity::getMontant);
    }

    @Test
    public void getCommissionById2() {
        CommissionEntity commission;

        commission = CommissionDAO.getInstance()
                .getById(AffaireDAO.getInstance().getById(ID_AFFAIRE_TO_GET),
                        ApporteurDAO.getInstance().getById(ID_APPORTEUR_TO_GET_2));

        assertThat(commission)
                .isNotNull()
                .returns(2D, CommissionEntity::getMontant);
    }

    @Test
    public void getCommissionById3() {
        CommissionEntity commission;

        commission = CommissionDAO.getInstance().getById(ID_AFFAIRE_TO_GET, ID_APPORTEUR_TO_GET_3);

        assertThat(commission)
                .isNotNull()
                .returns(3D, CommissionEntity::getMontant);
    }

    @Test
    public void getTotalByMonthAndApporteurId() {
        Double montantCommissions;

        montantCommissions = CommissionDAO.getInstance().getTotalByMonthAndApporteurId(1, 2023, 1L);

        assertThat(montantCommissions)
                .isNotNull()
                .isEqualTo(3D);
    }
}
