package fr.ul.projectvgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.AffaireDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.CommissionDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.SettingsDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.SettingEntity;
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
                INSERT INTO APPORTEUR (`ID`, `PRENOM`, `NOM`, `PARRAIN_ID`, `IS_DELETED`) VALUES
                (1, 'Antony', 'DUDU', NULL, 0),
                (2, 'Margot', 'DUMOULING', 1, 0),
                (3, 'Bastieng', 'CHAT FAOU ZER', 1, 0),
                (4, 'Daddy', 'DADDY GOOD', NULL, 1),
                (5, 'Mustang', 'COQ', 4, 0),
                (6, 'Loic', 'VERSET', 4, 1),
                (7, 'Tutur', 'TROGNON', 4, 0);
                """;
        String insertAffaireQuery = """
                INSERT INTO affaire (`ID`, `APPORTEUR_ID`, `DATE`, `COMMISSION_GLOBALE`) VALUES
                (1, 1, '2023-01-11', 200),
                (2, 2, '2023-01-11', 150),
                (3, 1, '2022-12-14', 100),
                (4, 2, '2022-12-14', 175),
                (5, 2, '2022-11-16', 140);
                """;
        String insertCommissionsQuery = """
                INSERT INTO commission (`AFFAIRE_ID`, `APPORTEUR_ID`, `MONTANT`) VALUES
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
        String insertSettingsQuery = """
                INSERT INTO `parametres` (`ID`, `CODE`, `LABEL`, `VALEUR`) VALUES
                (1, 'NB_PARRAINGE_MAX', 'Niveau maximum de parrainage', '13'),
                (2, 'NB_MIN_AFFAIRES', 'Nombre d affaires minimum à apporter pour rester affilié', '1'),
                (3, 'DUREE_MIN_AFFILIE', 'Durée pour apporter une nouvelle affaire afin de rester affilié (en mois)', '3'),
                (5, 'COMMISSION', 'Pourcentage de commission touché par chaque niveau de parrain', '10'),
                (6, 'DIR_PARR_VALUE', 'Pourcentage de commission touché par un parrain direct', '0.05'),
                (7, 'INDIR_PARR_VALUE', 'Pourcentage de commission touché par un parrain indirect', '0.5');
                """;
    
    
        session.createNativeQuery("DELETE FROM COMMISSION", CommissionEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM AFFAIRE", AffaireEntity.class).executeUpdate();
        session.createNativeQuery("UPDATE APPORTEUR SET PARRAIN_ID = NULL", ApporteurEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM APPORTEUR", ApporteurEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM PARAMETRES", SettingEntity.class).executeUpdate();
    
        session.createNativeQuery(insertApporteurQuery, ApporteurEntity.class).executeUpdate();
        session.createNativeQuery(insertAffaireQuery, AffaireEntity.class).executeUpdate();
        session.createNativeQuery(insertCommissionsQuery, CommissionEntity.class).executeUpdate();
        session.createNativeQuery(insertSettingsQuery, SettingEntity.class).executeUpdate();
        
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
    public void getAllAvailableApporteurs() {
        assertThat(ApporteurDAO.getInstance().getAllAvailable())
                .isNotNull()
                .isNotEmpty()
                .hasSize(5)
                .containsExactly(
                        ApporteurEntity.builder().id(1L).build(),
                        ApporteurEntity.builder().id(2L).build(),
                        ApporteurEntity.builder().id(3L).build(),
                        ApporteurEntity.builder().id(5L).build(),
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
    
    @Test
    public void getAllSettings() {
        assertThat(SettingsDAO.getInstance().getAll())
                .isNotNull()
                .isNotEmpty()
                .hasSize(6)
                .containsExactly(
                        SettingEntity.builder().id(1L).build(),
                        SettingEntity.builder().id(2L).build(),
                        SettingEntity.builder().id(3L).build(),
                        SettingEntity.builder().id(5L).build(),
                        SettingEntity.builder().id(6L).build(),
                        SettingEntity.builder().id(7L).build()
                );
    }
}
