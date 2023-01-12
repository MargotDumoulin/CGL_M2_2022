package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;

import java.util.stream.Stream;

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

    public Stream<AffaireEntity> getAllByApporteurId(int pageSize, int start, Long apporteurId, String orderBy, String dir) {
        String query = "SELECT affaire FROM Affaire AS affaire WHERE affaire.apporteur.id = :apporteurId" + " ORDER BY " + orderBy + " " + dir;
        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setParameter("apporteurId", apporteurId)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultStream();
    }

    public Stream<AffaireEntity> getAllByApporteurId(int pageSize, int start, Long apporteurId) {
        String query = "SELECT affaire FROM Affaire AS affaire WHERE affaire.apporteur.id = :apporteurId";
        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setParameter("apporteurId", apporteurId)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .getResultStream();
    }

    public Stream<AffaireEntity> getAllByApporteurId(Long apporteurId) {
        String query = "SELECT affaire FROM Affaire AS affaire WHERE affaire.apporteur.id = :apporteurId";
        return getSession()
                .createQuery(query, AffaireEntity.class)
                .setParameter("apporteurId", apporteurId)
                .getResultStream();
    }

}
