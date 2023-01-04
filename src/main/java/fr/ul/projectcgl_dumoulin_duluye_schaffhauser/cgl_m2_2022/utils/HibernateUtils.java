package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.utils;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtils {
    private static Session session;
    public static Session getSession(){
        if (session == null || !session.isOpen()) {
            Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty("hibernate.connection.url", "jdbc:h2:D:/DEV/db/cgl1.db");
            hibernateProperties.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
            hibernateProperties.setProperty("hibernate.connection.username", "");
            hibernateProperties.setProperty("hibernate.connection.password", "");
            hibernateProperties.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            hibernateProperties.setProperty("connection.pool_size", "1");
            hibernateProperties.setProperty("show_sql", "true");
            session = new Configuration()
                    .addProperties(hibernateProperties)
                    .addAnnotatedClass(Apporteur.class)
                    .buildSessionFactory()
                    .openSession();
        }
        return session;
    }
}
