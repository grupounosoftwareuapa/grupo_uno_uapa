package com.uapa.software.utils;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import com.uapa.software.models.Project;
import com.uapa.software.models.Rol;
import com.uapa.software.models.Task;
import com.uapa.software.models.User;


public class HibernateTestUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.h2.Driver");
                settings.put(Environment.URL, "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                // Registra tus clases anotadas
                configuration.addAnnotatedClass(Project.class);
                configuration.addAnnotatedClass(Task.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Rol.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error creando la SessionFactory para pruebas");
            }
        }
        return sessionFactory;
    }
}