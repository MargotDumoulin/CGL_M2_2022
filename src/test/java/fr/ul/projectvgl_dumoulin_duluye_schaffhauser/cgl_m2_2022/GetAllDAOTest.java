package fr.ul.projectvgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.AffaireDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.CommissionDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllDAOTest {
    
    @BeforeAll
    public static void initTest() {
        Session session = HibernateUtils.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        
        String insertApporteurQuery = """
                INSERT INTO APPORTEUR (`ID`, `PRENOM`, `NOM`, `PARRAIN_ID`) VALUES
                (1, 'Antony', 'DUDU', NULL),
                (2, 'Margot', 'DUMOULING', 1),
                (3, 'Bastieng', 'CHAT FAOU ZER', 1),
                (4, 'Daddy', 'DADDY GOOD', NULL),
                (5, 'Mustang', 'COQ', 4),
                (6, 'Loic', 'VERSET', 4),
                (7, 'Tutur', 'TROGNON', 4);
                """;
        String insertAffaireQuery = """
                INSERT INTO AFFAIRE (`ID`, `APPORTEUR_ID`, `DATE`, `COMMISSION_GLOBALE`) VALUES
                (1, 1, '2023-01-11', 200),
                (2, 2, '2023-01-11', 150),
                (3, 1, '2022-12-14', 100),
                (4, 2, '2022-12-14', 175),
                (5, 2, '2022-11-16', 140);
                """;
        String insertCommissionsQuery = """
                INSERT INTO COMMISSION (`AFFAIRE_ID`, `APPORTEUR_ID`, `MONTANT`) VALUES
                (1, 1, 1),
                (1, 2, 3),
                (1, 3, 2),
                (2, 1, 2),
                (2, 3, 1),
                (3, 1, 3),
                (3, 2, 1),
                (3, 3, 2),
                (4, 5, 3),
                (4, 3, 2),
                (5, 5, 3),
                (5, 3, 2);
                """;
        
        session.createNativeQuery("DELETE FROM COMMISSION", CommissionEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM AFFAIRE", AffaireEntity.class).executeUpdate();
        session.createNativeQuery("UPDATE APPORTEUR SET PARRAIN_ID = NULL", ApporteurEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM APPORTEUR", ApporteurEntity.class).executeUpdate();
        
        session.createNativeQuery(insertApporteurQuery, ApporteurEntity.class).executeUpdate();
        session.createNativeQuery(insertAffaireQuery, AffaireEntity.class).executeUpdate();
        session.createNativeQuery(insertCommissionsQuery, CommissionEntity.class).executeUpdate();
        
        tx.commit();
    }
    
    @Test
    public void getAllApporteurs() {
        assertThat(ApporteurDAO.getInstance().getAll())
                .isNotNull()
                .isNotEmpty()
                .hasSize(7)
                .containsExactly(
                        ApporteurEntity.builder().id(1L).build(),
                        ApporteurEntity.builder().id(2L).build(),
                        ApporteurEntity.builder().id(3L).build(),
                        ApporteurEntity.builder().id(4L).build(),
                        ApporteurEntity.builder().id(5L).build(),
                        ApporteurEntity.builder().id(6L).build(),
                        ApporteurEntity.builder().id(7L).build()
                );
    }
    @Test
    public void getAllAffaires() {
        assertThat(AffaireDAO.getInstance().getAll())
                .isNotNull()
                .isNotEmpty()
                .hasSize(5)
                .containsExactly(
                        AffaireEntity.builder().id(1L).build(),
                        AffaireEntity.builder().id(2L).build(),
                        AffaireEntity.builder().id(3L).build(),
                        AffaireEntity.builder().id(4L).build(),
                        AffaireEntity.builder().id(5L).build()
                );
    }
    @Test
    public void getAllCommissions() {
        assertThat(CommissionDAO.getInstance().getAll())
                .isNotNull()
                .isNotEmpty()
                .hasSize(12);
    }
    
    @Test
    public void getAllAffairesPaginated() {
        assertThat(AffaireDAO.getInstance().getAll(2, 1))
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(
                        AffaireEntity.builder().id(2L).build(),
                        AffaireEntity.builder().id(3L).build()
                );
    }
    @Test
    public void getAllAffairesSortedByCommFilteredByAppId() {
        assertThat(AffaireDAO.getInstance().getAllDirByAppId(5, 0, 2L, "commissionGlobale", "desc", false))
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(
                        AffaireEntity.builder().id(4L).build(),
                        AffaireEntity.builder().id(2L).build(),
                        AffaireEntity.builder().id(5L).build()
                );
    }
    @Test
    public void getAllAffairesFilteredByAppId() {
        assertThat(AffaireDAO.getInstance().getAllDirByAppId(2L))
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(
                        AffaireEntity.builder().id(2L).build(),
                        AffaireEntity.builder().id(4L).build(),
                        AffaireEntity.builder().id(5L).build()
                );
    }

    @Test
    public void getAllAffairesPaginatedFilteredByAppId() {
        assertThat(AffaireDAO.getInstance().getAllByAppId(2, 0, 1L, "date", "asc", false))
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(
                        AffaireEntity.builder().id(3L).build(),
                        AffaireEntity.builder().id(1L).build()
                );
    }

    @Test
    public void getAllAffairesFilteredByCommAppId() {
        assertThat(AffaireDAO.getInstance().getAllByAppId(5L))
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .containsExactly(
                        AffaireEntity.builder().id(4L).build(),
                        AffaireEntity.builder().id(5L).build()
                );
    }
}
