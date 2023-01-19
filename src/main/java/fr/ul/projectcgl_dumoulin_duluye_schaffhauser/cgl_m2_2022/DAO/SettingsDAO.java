package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.SettingEntity;

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

    public SettingEntity getByCode(String code) {
        String query = """
            SELECT setting
            FROM Setting AS setting
            WHERE setting.code = :code
            """;

        return getSession()
                .createQuery(query, SettingEntity.class)
                .setParameter("code", code)
                .getSingleResult();
    }

    public SettingEntity update(Long id, String value){
        SettingEntity settingEntity = getById(id);

        settingEntity.setValeur(value);
        
        return super.update(settingEntity);
    }
}
