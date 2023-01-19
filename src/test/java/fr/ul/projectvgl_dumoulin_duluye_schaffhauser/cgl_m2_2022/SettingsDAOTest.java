package fr.ul.projectvgl_dumoulin_duluye_schaffhauser.cgl_m2_2022;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.SettingsDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.SettingEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SettingsDAOTest {
    
    private static final String CODE_TO_GET = "NB_PARRAINGE_MAX";
    private static final String CODE_TO_UPDATE = "DIR_PARR_VALUE";
    private static final long ID_TO_UPDATE = 6;
    
    @BeforeAll
    public static void initTest() {
        Session session = HibernateUtils.getInstance().getSession();
        Transaction tx = session.beginTransaction();
        
        String insertSettingsQuery = """
                INSERT INTO `parametres` (`ID`, `CODE`, `LABEL`, `VALEUR`) VALUES
                (1, 'NB_PARRAINGE_MAX', 'Niveau maximum de parrainage', '13'),
                (2, 'NB_MIN_AFFAIRES', 'Nombre d affaires minimum à apporter pour rester affilié', '1'),
                (3, 'DUREE_MIN_AFFILIE', 'Durée pour apporter une nouvelle affaire afin de rester affilié (en mois)', '3'),
                (5, 'COMMISSION', 'Pourcentage de commission touché par chaque niveau de parrain', '10'),
                (6, 'DIR_PARR_VALUE', 'Pourcentage de commission touché par un parrain direct', '0.05'),
                (7, 'INDIR_PARR_VALUE', 'Pourcentage de commission touché par un parrain indirect', '0.5');
                """;
        
        session.createNativeQuery("DELETE FROM parametres", SettingEntity.class).executeUpdate();
        
        session.createNativeQuery(insertSettingsQuery, SettingEntity.class).executeUpdate();
        
        tx.commit();
    }
    
    @Test
    public void getByCode() {
        SettingEntity setting;
        
        setting = SettingsDAO.getInstance().getByCode(CODE_TO_GET);
        
        assertThat(setting)
                .isNotNull()
                .returns(1L, SettingEntity::getId)
                .returns("NB_PARRAINGE_MAX", SettingEntity::getCode)
                .returns("Niveau maximum de parrainage", SettingEntity::getLabel)
                .returns("13", SettingEntity::getValeur);
    }
    
    @Test
    public void update() {
        SettingEntity setting;
        
        SettingsDAO.getInstance().update(ID_TO_UPDATE, "Valeur de test");
        
        setting = SettingsDAO.getInstance().getByCode(CODE_TO_UPDATE);
        
        assertThat(setting)
                .isNotNull()
                .returns(ID_TO_UPDATE, SettingEntity::getId)
                .returns(CODE_TO_UPDATE, SettingEntity::getCode)
                .returns("Pourcentage de commission touché par un parrain direct", SettingEntity::getLabel)
                .returns("Valeur de test", SettingEntity::getValeur);
    }
}
