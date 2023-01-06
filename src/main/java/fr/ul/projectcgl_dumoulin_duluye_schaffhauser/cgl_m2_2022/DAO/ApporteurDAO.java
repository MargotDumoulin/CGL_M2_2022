package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils.HibernateUtils;

import java.util.stream.Stream;

public class ApporteurDAO {

    public static Stream<ApporteurEntity> getAll() {
        return HibernateUtils.getSession()
                .createQuery("from Apporteur ", ApporteurEntity.class)
                .getResultStream();
    }
}
