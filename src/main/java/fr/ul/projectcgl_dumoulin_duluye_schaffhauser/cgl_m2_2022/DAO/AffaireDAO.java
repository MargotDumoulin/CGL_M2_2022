package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;

public class AffaireDAO extends AbstractDAO<AffaireEntity, Long> {
    private static AffaireDAO instance;

    public static AffaireDAO getInstance() {
        if (instance == null) {
            instance = new AffaireDAO();
        }

        return instance;
    }

    private AffaireDAO() {
        super(AffaireEntity.class);
    }

}
