package fr.ul.projectvgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApporteurDAOTest {

    private static final long ID_TO_GET = 3;
    private static final long PARRAIN_ID = 3;
    private static final long ID_TO_UPDATE = 2;
    private static final long ID_TO_DELETE = 4;
    private static final long ID_TO_DELETE_2 = 7;
    private static final long ID_TO_EXIST_TRUE = 3;
    private static final long ID_TO_EXIST_FALSE = 0;

    @BeforeAll
    public static void initTest() {
        Session session = HibernateUtils.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        String insertQuery = """
                INSERT INTO APPORTEUR (`ID`, `PRENOM`, `NOM`, `PARRAIN_ID`) VALUES
                (1, 'Antony', 'DUDU', NULL),
                (2, 'Margot', 'DUMOULING', 1),
                (3, 'Bastieng', 'CHAT FAOU ZER', 1),
                (4, 'Daddy', 'DADDY GOOD', NULL),
                (5, 'Mustang', 'COQ', 4),
                (6, 'Loic', 'VERSET', 4),
                (7, 'Tutur', 'TROGNON', 4);
                """;


        session.createNativeQuery("DELETE FROM COMMISSION", CommissionEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM AFFAIRE", AffaireEntity.class).executeUpdate();
        session.createNativeQuery("UPDATE APPORTEUR SET PARRAIN_ID = NULL", ApporteurEntity.class).executeUpdate();
        session.createNativeQuery("DELETE FROM APPORTEUR", ApporteurEntity.class).executeUpdate();

        session.createNativeQuery(insertQuery, ApporteurEntity.class).executeUpdate();

        tx.commit();
    }

    @Test
    public void getById() {
        ApporteurEntity apporteur;

        apporteur = ApporteurDAO.getInstance().getById(ID_TO_GET);

        assertThat(apporteur)
                .isNotNull()
                .doesNotReturn(null, ApporteurEntity::getId)
                .returns(ID_TO_GET, ApporteurEntity::getId)
                .returns("CHAT FAOU ZER", ApporteurEntity::getNom)
                .returns("Bastieng", ApporteurEntity::getPrenom)
                .doesNotReturn(null, ApporteurEntity::getParrain)
                .doesNotReturn(null, e -> e.getParrain().getId())
                .returns(1L, e -> e.getParrain().getId());
    }

    @Test
    public void insertApporteur() {
        ApporteurEntity apporteurSave;
        ApporteurEntity apporteurGet;

        apporteurSave = ApporteurEntity.builder()
                .id(null)
                .nom("CHALUMEAU")
                .prenom("Timothée")
                .parrain(null)
                .build();

        ApporteurDAO.getInstance().insert(apporteurSave);
        apporteurGet = ApporteurDAO.getInstance().getById(apporteurSave.getId());

        assertThat(apporteurSave)
                .isNotNull()
                .doesNotReturn(null, ApporteurEntity::getId);

        assertThat(apporteurGet)
                .isNotNull()
                .doesNotReturn(null, ApporteurEntity::getId)
                .returns(apporteurSave.getId(), ApporteurEntity::getId)
                .returns("CHALUMEAU", ApporteurEntity::getNom)
                .returns("Timothée", ApporteurEntity::getPrenom)
                .returns(null, ApporteurEntity::getParrain);
    }

    @Test
    public void updateApporteur() {
        ApporteurEntity apporteur;

        apporteur = ApporteurDAO.getInstance().getById(ID_TO_UPDATE);
        apporteur.setNom("Calumet");
        apporteur.setPrenom("Timothée");
        apporteur.setParrain(ApporteurDAO.getInstance().getById(PARRAIN_ID));
        ApporteurDAO.getInstance().update(apporteur);

        assertThat(apporteur)
                .isNotNull()
                .doesNotReturn(null, ApporteurEntity::getId)
                .returns(ID_TO_UPDATE, ApporteurEntity::getId)
                .returns("Calumet", ApporteurEntity::getNom)
                .returns("Timothée", ApporteurEntity::getPrenom)
                .doesNotReturn(null, ApporteurEntity::getParrain)
                .doesNotReturn(null, e -> e.getParrain().getId())
                .returns(PARRAIN_ID, e -> e.getParrain().getId());
    }

    @Test
    public void deleteApporteurByEntity() {
        boolean isDeleted;

        // Suppression de l'apporteur X

        isDeleted = ApporteurDAO.getInstance().delete(ApporteurDAO.getInstance().getById(ID_TO_DELETE));

        assertThat(isDeleted).isFalse();
        assertThat(ApporteurDAO.getInstance().isPresent(ID_TO_DELETE)).isFalse();

        // Verification que personne n'a X en parrainS

        assertThat(ApporteurDAO.getInstance().getAll())
                .isNotNull()
                .noneMatch(e -> e.getParrain() != null && e.getParrain().getId() == ID_TO_DELETE);
    }
    @Test
    public void deleteApporteurById() {
        boolean isDeleted;

        // Suppression de l'apporteur X

        isDeleted = ApporteurDAO.getInstance().delete(ID_TO_DELETE_2);

        assertThat(isDeleted).isFalse();
        assertThat(ApporteurDAO.getInstance().isPresent(ID_TO_DELETE_2)).isFalse();

        // Verification que personne n'a X en parrain

        assertThat(ApporteurDAO.getInstance().getAll())
                .isNotNull()
                .noneMatch(e -> e.getParrain() != null && e.getParrain().getId() == ID_TO_DELETE_2);
    }

    @Test
    public void isPresentApporteurTrue() {
        assertThat(ApporteurDAO.getInstance().isPresent(ID_TO_EXIST_TRUE))
                .isTrue();
    }

    @Test
    public void isPresentApporteurFalse() {
        assertThat(ApporteurDAO.getInstance().isPresent(ID_TO_EXIST_FALSE))
                .isFalse();
    }
}
