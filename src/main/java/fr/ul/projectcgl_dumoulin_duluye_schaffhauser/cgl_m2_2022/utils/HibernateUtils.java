package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.CommissionEntity;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtils {
    private static Session session;

    public static Session getSession() {
        if (session == null || !session.isOpen()) {
            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty("hibernate.connection.url", "jdbc:mariadb://localhost:3307/projet_cgl");
            hibernateProperties.setProperty("dialect", "org.hibernate.dialect.MariaDBDialect");
            hibernateProperties.setProperty("hibernate.connection.username", "root");
            hibernateProperties.setProperty("hibernate.connection.password", "");
            hibernateProperties.setProperty("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver");
            hibernateProperties.setProperty("connection.pool_size", "1");
            hibernateProperties.setProperty("show_sql", "true");
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
