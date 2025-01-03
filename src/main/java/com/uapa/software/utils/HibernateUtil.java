package com.uapa.software.utils;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import com.uapa.software.models.Project;
import com.uapa.software.models.Rol;
import com.uapa.software.models.Task;
import com.uapa.software.models.User;


public class HibernateUtil {

	private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, EnvConfig.get("DB_URL"));
                settings.put(Environment.USER, EnvConfig.get("DB_USER"));
                settings.put(Environment.PASS, EnvConfig.get("DB_PASSWORD"));
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
