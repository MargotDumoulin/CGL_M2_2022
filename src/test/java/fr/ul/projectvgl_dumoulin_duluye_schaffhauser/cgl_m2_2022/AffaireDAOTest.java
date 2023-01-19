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

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class AffaireDAOTest {

    private static final long ID_TO_GET = 1;
    private static final long ID_TO_UPDATE = 2;
    private static final long ID_TO_DELETE = 3;
    private static final long ID_TO_DELETE_2 = 4;
    private static final long ID_APPORTEUR = 3;
    private static final long ID_TO_EXIST_TRUE = 1;
    private static final long ID_TO_EXIST_FALSE = 0;

    @BeforeAll
    public static void initTest() {
        Session session = HibernateUtils.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        String insertApporteurQuery = """
                INSERT INTO APPORTEUR (`ID`, `PRENOM`, `NOM`, `PARRAIN_ID`) VALUES
                (1, 'Antony', 'DUDU', NULL),
                (2, 'Margot', 'DUMOULING', 1),
                (3, 'Bastieng', 'CHAT FAOU ZER', 1);
                """;
        String insertAffaireQuery = """
                INSERT INTO AFFAIRE (`ID`, `APPORTEUR_ID`, `DATE`, `COMMISSION_GLOBALE`) VALUES
                (1, 1, '2023-01-11', 200),
                (2, 1, '2023-01-11', 200),
                (3, 2, '2022-12-14', 200),
                (4, 2, '2022-12-14', 200),
                (5, 2, '2022-11-16', 200);
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
                (4, 2, 3),
                (4, 3, 2),
                (5, 1, 3),
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
    public void getById() {
        AffaireEntity affaire;

        affaire = AffaireDAO.getInstance().getById(ID_TO_GET);

        assertThat(affaire)
                .isNotNull()
                .doesNotReturn(null, AffaireEntity::getId)
                .returns(ID_TO_GET, AffaireEntity::getId)
                .returns(1L, e -> e.getApporteur().getId())
                .returns("2023-01-11 00:00:00.0", e -> String.valueOf(e.getDate()))
                .returns(200D, AffaireEntity::getCommissionGlobale);
    }

    @Test
    public void insertAffaire() {
        AffaireEntity affaireSave;
        AffaireEntity affaireGet;
        Date date = new Date(1663711200000L);

        affaireSave = AffaireEntity.builder()
                .id(null)
                .apporteur(ApporteurDAO.getInstance().getById(ID_APPORTEUR))
                .date(date)
                .commissionGlobale(195.0)
                .build();

        AffaireDAO.getInstance().insert(affaireSave);
        affaireGet = AffaireDAO.getInstance().getById(affaireSave.getId());

        assertThat(affaireSave)
                .isNotNull()
                .doesNotReturn(null, AffaireEntity::getId);

        assertThat(affaireGet)
                .isNotNull()
                .doesNotReturn(null, AffaireEntity::getId)
                .returns(affaireSave.getId(), AffaireEntity::getId)
                .returns(ID_APPORTEUR, e -> e.getApporteur().getId())
                .returns("2022-09-21 00:00:00.0", e -> String.valueOf(e.getDate()))
                .returns(195.0, AffaireEntity::getCommissionGlobale);
    }

    @Test
    public void updateAffaire() {
        AffaireEntity affaire;
        Date date = new Date(1663711200000L);

        affaire = AffaireDAO.getInstance().getById(ID_TO_UPDATE);
        affaire.setApporteur(ApporteurDAO.getInstance().getById(ID_APPORTEUR));
        affaire.setDate(date);
        affaire.setCommissionGlobale(12.8);
        AffaireDAO.getInstance().update(affaire);

        assertThat(affaire)
                .isNotNull()
                .doesNotReturn(null, AffaireEntity::getId)
                .returns(ID_TO_UPDATE, AffaireEntity::getId)
                .doesNotReturn(null, AffaireEntity::getApporteur)
                .returns(ID_APPORTEUR, e -> e.getApporteur().getId())
                .returns(date, AffaireEntity::getDate)
                .returns(12.8, AffaireEntity::getCommissionGlobale);
    }

    @Test
    public void deleteAffaireByEntity() {
        boolean isPresent;

        // Suppression de l'apporteur X

        isPresent = AffaireDAO.getInstance().delete(AffaireDAO.getInstance().getById(ID_TO_DELETE));

        assertThat(isPresent).isFalse();
        assertThat(AffaireDAO.getInstance().isPresent(ID_TO_DELETE)).isFalse();

        // Verification que personne n'a X en parrain

        assertThat(CommissionDAO.getInstance().getAll())
                .isNotNull()
                .noneMatch(e -> e.getId().getAffaire() != null && e.getId().getAffaire().getId() == ID_TO_DELETE);
    }
    @Test
    public void deleteAffaireById() {
        boolean isPresent;

        // Suppression de l'apporteur X

        isPresent = AffaireDAO.getInstance().delete(ID_TO_DELETE_2);

        assertThat(isPresent).isFalse();
        assertThat(AffaireDAO.getInstance().isPresent(ID_TO_DELETE_2)).isFalse();

        // Verification que personne n'a X en parrain

        assertThat(CommissionDAO.getInstance().getAll())
                .isNotNull()
                .noneMatch(e -> e.getId().getAffaire() != null && e.getId().getAffaire().getId() == ID_TO_DELETE_2);
    }

    @Test
    public void isPresentAffaireTrue() {
        assertThat(AffaireDAO.getInstance().isPresent(ID_TO_EXIST_TRUE))
                .isTrue();
    }

    @Test
    public void isPresentAffaireFalse() {
        assertThat(AffaireDAO.getInstance().isPresent(ID_TO_EXIST_FALSE))
                .isFalse();
    }
}
