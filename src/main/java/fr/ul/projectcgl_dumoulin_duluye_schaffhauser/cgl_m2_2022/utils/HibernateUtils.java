package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtils {

    private static HibernateUtils instance;

    public static HibernateUtils getInstance() {
        if (instance == null) {
            instance = new HibernateUtils();
        }

        return instance;
    }

    private HibernateUtils() {
        try {
            Properties properties = new Properties();
            properties.load(HibernateUtils.class.getClassLoader().getResourceAsStream("application.properties"));

            url = properties.getProperty("hibernate.connection.url");
            dialect = properties.getProperty("dialect");
            username = properties.getProperty("hibernate.connection.username");
            password = properties.getProperty("hibernate.connection.password");
            driver = properties.getProperty("hibernate.connection.driver_class");
            show_sql = properties.getProperty("show_sql");
        }
        catch (Exception e) {
            url = "jdbc:mariadb://localhost:3307/projet_cgl";
            dialect = "org.hibernate.dialect.MariaDBDialect";
            username = "root";
            password = "";
            driver = "org.mariadb.jdbc.Driver";
            show_sql = "true";
        }
    }

    private String url;
    private String dialect;
    private String username;
    private String password;
    private String driver;
    private String show_sql;

    private Session session;

    public Session getSession() {
        if (session == null || !session.isOpen()) {
            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty("hibernate.connection.url", url);
            hibernateProperties.setProperty("dialect", dialect);
            hibernateProperties.setProperty("hibernate.connection.username", username);
            hibernateProperties.setProperty("hibernate.connection.password", password);
            hibernateProperties.setProperty("hibernate.connection.driver_class", driver);
            hibernateProperties.setProperty("connection.pool_size", "1");
            hibernateProperties.setProperty("show_sql", show_sql);
            session = new Configuration()
                    .addProperties(hibernateProperties)
                    .addAnnotatedClass(ApporteurEntity.class)
                    .addAnnotatedClass(AffaireEntity.class)
                    .addAnnotatedClass(CommissionEntity.class)
                    .buildSessionFactory()
                    .openSession();
        }
        return session;
    }
}
