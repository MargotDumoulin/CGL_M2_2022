package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.SettingEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Stream;

public class SettingsDAO extends AbstractDAO<SettingEntity, Long> {

    private static SettingsDAO instance;

    public static SettingsDAO getInstance() {
        if (instance == null) {
            instance = new SettingsDAO();
        }

        return instance;
    }

    private SettingsDAO() {
        super(SettingEntity.class);
    }

    public void update(Long id, String value){
        Session session = getSession();
        Transaction tx = session.beginTransaction();

        SettingEntity settingEntity =  (SettingEntity) session.load(SettingEntity.class, id);

        settingEntity.setValeur(value);
        session.save(settingEntity);

        tx.commit();
        session.close();
    }
}
