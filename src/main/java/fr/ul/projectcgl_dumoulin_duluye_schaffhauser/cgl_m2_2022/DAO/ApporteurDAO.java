package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    
    public Stream<ApporteurEntity> getAll(int pageSize, int start, String orderBy, String dir) {
        if (orderBy.matches("affilie")) {
            return this.getAllOrderedByAffilie(pageSize, start, dir);
        } else {
            return this.getAllClassic(pageSize, start, orderBy, dir);
        }
    }
    
    public Stream<ApporteurEntity> getAllAvailable() {
        String hqlQuery = """
                SELECT apporteur
                FROM Apporteur AS apporteur
                WHERE apporteur.isDeleted = false
                """;
        
        return getSession()
                .createQuery(hqlQuery, ApporteurEntity.class)
                .getResultStream();
    }
    
    public Long getAllNbOfResults() {
        String hqlQuery = "SELECT COUNT(*) FROM Apporteur ";
        
        return getSession()
                .createQuery(hqlQuery, Long.class)
                .getSingleResult();
    }
    
    public Stream<ApporteurEntity> getAllClassic(int pageSize, int start, String orderBy, String dir) {
        String orderByFormatted = orderBy;
        LocalDate dateToFilterWith = this.getLocalDateForOrderBy(orderBy);
        
        if (orderBy.contains("total")) {
            orderByFormatted = " COALESCE(SUM(c.MONTANT), 0) ";
        }
        
        String sqlQuery = """
                SELECT t_apporteur.*
                FROM apporteur AS t_apporteur
                LEFT JOIN (SELECT t_commission.*
                           FROM commission t_commission
                           LEFT JOIN affaire AS t_affaire ON t_commission.AFFAIRE_ID = t_affaire.ID
                           WHERE YEAR(t_affaire.DATE) = :year
                             AND MONTH(t_affaire.DATE) = :month) AS c ON c.APPORTEUR_ID = t_apporteur.ID
                GROUP BY t_apporteur.id
                """;
        sqlQuery += " ORDER BY " + orderByFormatted + " " + dir;
        
        return getSession()
                .createNativeQuery(sqlQuery, ApporteurEntity.class)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .setParameter("month", dateToFilterWith.getMonthValue())
                .setParameter("year", dateToFilterWith.getYear())
                .getResultStream();
        
    }
    
    public LocalDate getLocalDateForOrderBy(String orderBy) {
        LocalDate currentDate = LocalDate.now();
        
        switch (orderBy) {
            case "totalCommissionsMM1":
                return LocalDate.from(currentDate).minusMonths(1);
            case "totalCommissionsMM2":
                return LocalDate.from(currentDate).minusMonths(2);
            default:
                return currentDate;
        }
    }
    
    public Stream<ApporteurEntity> getAllOrderedByAffilie(int pageSize, int start, String dir) {
        Long durationSetting = Long.parseLong(SettingsDAO.getInstance().getByCode("DUREE_MIN_AFFILIE").getValeur());
        LocalDate compDate = LocalDate.from(LocalDate.now()).minusMonths(durationSetting);
        
        String sqlQuery = """
                 SELECT apporteur.*
                 FROM apporteur
                 LEFT JOIN(
                     SELECT COUNT(affaire.id) AS nb_affaires, apporteur.ID
                     FROM apporteur, affaire
                     WHERE apporteur.ID = affaire.APPORTEUR_ID AND affaire.DATE >= :comparativeDate
                 ) AS a ON a.ID = apporteur.ID
                """;
        
        sqlQuery += " ORDER BY a.nb_affaires " + dir;
        
        return getSession()
                .createNativeQuery(sqlQuery, ApporteurEntity.class)
                .setFirstResult(start)
                .setMaxResults(pageSize)
                .setParameter("comparativeDate", Date.from(compDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .getResultStream();
    }
    
    public Boolean getIsAffilie(Long apporteurId) {
        Long durationSetting = Long.parseLong(SettingsDAO.getInstance().getByCode("DUREE_MIN_AFFILIE").getValeur());
        Long minAffairesSetting = Long.parseLong(SettingsDAO.getInstance().getByCode("NB_MIN_AFFAIRES").getValeur());
        LocalDate compDate = LocalDate.from(LocalDate.now()).minusMonths(durationSetting);
        
        String hqlQuery = "" +
                "SELECT COUNT(affaire.id) " +
                "FROM Apporteur AS apporteur, Affaire AS affaire " +
                "WHERE apporteur.id = affaire.apporteur.id " +
                "AND apporteur.id = :apporteurId " +
                "AND affaire.date >= :comparativeDate";
        
        Long nbOfAffaires = getSession()
                .createQuery(hqlQuery, Long.class)
                .setParameter("apporteurId", apporteurId)
                .setParameter("comparativeDate", Date.from(compDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .getSingleResult();
        
        return nbOfAffaires >= minAffairesSetting;
    }

    public Stream<ApporteurEntity> getAllApporteursWithMaxChainLength(){
        int length = Integer.parseInt(SettingsDAO.getInstance().getByCode("NB_PARRAINGE_MAX").getValeur());

        return getAllApporteursWithMaxChainLength(length);
    }
    public Stream<ApporteurEntity> getAllApporteursWithMaxChainLength(int length) {
        String sqlQuery = "" +
                "WITH RECURSIVE cte (id, prenom, nom, parrain_id, is_deleted, depth) AS (" +
                "SELECT id, prenom, nom, parrain_id, is_deleted, 0 FROM apporteur WHERE parrain_id IS NULL " +
                "UNION ALL " +
                "SELECT a.id, a.prenom, a.nom, a.parrain_id, a.is_deleted, cte.depth + 1 FROM apporteur a " +
                "JOIN cte ON a.parrain_id = cte.id" +
                ")" +
                "SELECT id, prenom, nom, parrain_id, is_deleted FROM cte " +
                "WHERE depth < :maxLength and is_deleted = FALSE";

        Stream<ApporteurEntity> apporteurs = getSession()
                .createNativeQuery(sqlQuery, ApporteurEntity.class)
                .setParameter("maxLength", length)
                .getResultStream();

        return apporteurs;
    }
    
    @Override
    public boolean isPresent(Long id) {
        return Optional.ofNullable(getById(id))
                .map(ApporteurEntity::isDeleted)
                .map(e -> !e)
                .orElse(false);
    }
    
    @Override
    public boolean delete(ApporteurEntity entity) {
        entity.setDeleted(true);
        
        try (Session session = getSession()) {
            persistEntity(session.merge(entity));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return isPresent(entity.getId());
    }
    
    @Override
    public boolean delete(Long id) {
        return delete(getById(id));

    }
}
