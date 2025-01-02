package com.uapa.software.utils;

public class HibernateUtil {

//    private static SessionFactory sessionFactory;
//
//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null) {
//            try {
//                Configuration configuration = new Configuration();
//
//                // Hibernate settings equivalent to hibernate.cfg.xml
//                Properties settings = new Properties();
//                settings.put(AvailableSettings.DRIVER, "org.postgresql.Driver");
//                settings.put(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/your_database");
//                settings.put(AvailableSettings.USER, "your_user");
//                settings.put(AvailableSettings.PASS, "your_password");
//                settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
//
//                // Hibernate behavior
//                settings.put(AvailableSettings.SHOW_SQL, "true");
//                settings.put(AvailableSettings.HBM2DDL_AUTO, "update"); // `validate`, `create`, `create-drop`, `update`
//
//                configuration.setProperties(settings);
//
//                // Register annotated classes
//                configuration.addAnnotatedClass(Project.class);
//                configuration.addAnnotatedClass(Task.class);
//                configuration.addAnnotatedClass(User.class);
//                configuration.addAnnotatedClass(Rol.class);
//
//                sessionFactory = configuration.buildSessionFactory();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException("There was an issue building the SessionFactory");
//            }
//        }
//        return sessionFactory;
//    }
}
