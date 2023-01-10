package fr.ul.projectvgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApporteurDAOTest {

    private static final long ID_TO_GET = 3;
    private static final long PARRAIN_ID = 1;
    private static final long ID_TO_UPDATE = 2;
    private static final long ID_TO_DELETE = 4;
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

        session.createNativeQuery("UPDATE APPORTEUR SET PARRAIN_ID = NULL").executeUpdate();
        session.createNativeQuery("DELETE FROM APPORTEUR").executeUpdate();
        session.createNativeQuery(insertQuery).executeUpdate();

        tx.commit();
    }

    @Test
    public void getById() {
        ApporteurEntity apporteur;

        apporteur = ApporteurDAO.getById(ID_TO_GET);

        assertThat(apporteur).isNotNull()
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
                .nom("Chalumeau")
                .prenom("Timothée")
                .parrain(null)
                .build();

        ApporteurDAO.insertApporteur(apporteurSave);
        apporteurGet = ApporteurDAO.getById(apporteurSave.getId());

        assertThat(apporteurSave).isNotNull()
                .doesNotReturn(null, ApporteurEntity::getId);

        assertThat(apporteurGet).isNotNull()
                .doesNotReturn(null, ApporteurEntity::getId)
                .returns(apporteurSave.getId(), ApporteurEntity::getId)
                .returns("Chalumeau", ApporteurEntity::getNom)
                .returns("Timothée", ApporteurEntity::getPrenom)
                .returns(null, ApporteurEntity::getParrain);
    }

    @Test
    public void updateApporteur() {
        ApporteurEntity apporteur;

        apporteur = ApporteurEntity.builder()
                .id(ID_TO_UPDATE)
                .nom("Calumet")
                .prenom("Bastien")
                .parrain(ApporteurDAO.getById(PARRAIN_ID))
                .build();
        ApporteurDAO.updateApporteur(apporteur);

        assertThat(apporteur).isNotNull()
                .doesNotReturn(null, ApporteurEntity::getId)
                .returns(ID_TO_UPDATE, ApporteurEntity::getId)
                .returns("Calumet", ApporteurEntity::getNom)
                .returns("Bastien", ApporteurEntity::getPrenom)
                .doesNotReturn(null, ApporteurEntity::getParrain)
                .doesNotReturn(null, e -> e.getParrain().getId())
                .returns(PARRAIN_ID, e -> e.getParrain().getId());
    }

    @Test
    public void deleteApporteur() {
        boolean isDeleted;

        // Suppression de l'apporteur X

        isDeleted = ApporteurDAO.deleteApporteur(ApporteurDAO.getById(ID_TO_DELETE));

        assertThat(isDeleted).isFalse();
        assertThat(ApporteurDAO.isPresentApporteur(ID_TO_DELETE)).isFalse();

        // Verification que personne n'a X en parrain

        assertThat(ApporteurDAO.getAll()).isNotNull()
                .noneMatch(e -> e.getParrain() != null && e.getParrain().getId() == ID_TO_DELETE);
    }

    @Test
    public void isPresentApporteurTrue() {
        assertThat(ApporteurDAO.isPresentApporteur(ID_TO_EXIST_TRUE)).isTrue();
    }

    @Test
    public void isPresentApporteurFalse() {
        assertThat(ApporteurDAO.isPresentApporteur(ID_TO_EXIST_FALSE)).isFalse();
    }
}
