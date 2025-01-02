package com.uapa.software.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import com.uapa.software.models.Project;
import com.uapa.software.models.Rol;
import com.uapa.software.models.Task;
import com.uapa.software.models.User;

import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/your_database");
                settings.put(Environment.USER, "your_user");
                settings.put(Environment.PASS, "your_password");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

                // Hibernate behavior
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update"); // `validate`, `create`, `create-drop`, `update`

                configuration.setProperties(settings);

                // Register annotated classes
                configuration.addAnnotatedClass(Project.class);
                configuration.addAnnotatedClass(Task.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Rol.class);

                sessionFactory = configuration.buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("There was an issue building the SessionFactory");
            }
        }
        return sessionFactory;
    }
}
