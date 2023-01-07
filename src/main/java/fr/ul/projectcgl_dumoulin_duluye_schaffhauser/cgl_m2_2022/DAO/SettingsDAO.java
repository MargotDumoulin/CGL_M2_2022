package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.SettingEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Setting;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Stream;

public class SettingsDAO {
    public static List<SettingEntity> getAll(){
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSession();
            transaction = session.beginTransaction();
            List<SettingEntity> settings = session.createQuery("FROM Setting ", SettingEntity.class).list();
            transaction.commit();
            return settings;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
