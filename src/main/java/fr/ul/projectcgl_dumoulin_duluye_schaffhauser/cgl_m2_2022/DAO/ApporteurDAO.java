package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;

public class ApporteurDAO extends AbstractDAO<ApporteurEntity, Long> {

    private static ApporteurDAO instance;

    public static ApporteurDAO getInstance() {
        if (instance == null) {
            instance = new ApporteurDAO();
        }

        return instance;
    }

    private ApporteurDAO() {
        super(ApporteurEntity.class);
    }
}
