package com.uapa.software;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uapa.software.models.Project;
import com.uapa.software.models.Rol;
import com.uapa.software.models.Task;
import com.uapa.software.models.User;
import com.uapa.software.utils.HibernateTestUtil;

class HibernateTestUtilTest {

    private Configuration mockedConfiguration;

    @BeforeEach
    void setUp() {
        mockedConfiguration = spy(new Configuration());
    }

    @Test
    void testGetSessionFactoryNotNull() {
        // Act
        SessionFactory sessionFactory = HibernateTestUtil.getSessionFactory();

        // Assert
        assertNotNull(sessionFactory, "SessionFactory should not be null");
    }

    @Test
    void testSessionFactoryConfiguration() {
        // Mock Configuration to ensure that properties and annotated classes are set
        doReturn(mock(SessionFactory.class)).when(mockedConfiguration).buildSessionFactory();

        mockedConfiguration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        mockedConfiguration.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        mockedConfiguration.setProperty("hibernate.connection.username", "sa");
        mockedConfiguration.setProperty("hibernate.connection.password", "");
        mockedConfiguration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        mockedConfiguration.setProperty("hibernate.show_sql", "true");
        mockedConfiguration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        mockedConfiguration.addAnnotatedClass(Project.class);
        mockedConfiguration.addAnnotatedClass(Task.class);
        mockedConfiguration.addAnnotatedClass(User.class);
        mockedConfiguration.addAnnotatedClass(Rol.class);

        // Verify the properties are applied
        verify(mockedConfiguration).setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        verify(mockedConfiguration).setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        verify(mockedConfiguration).setProperty("hibernate.connection.username", "sa");
        verify(mockedConfiguration).setProperty("hibernate.connection.password", "");
        verify(mockedConfiguration).setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        verify(mockedConfiguration).setProperty("hibernate.show_sql", "true");
        verify(mockedConfiguration).setProperty("hibernate.hbm2ddl.auto", "create-drop");

        verify(mockedConfiguration).addAnnotatedClass(Project.class);
        verify(mockedConfiguration).addAnnotatedClass(Task.class);
        verify(mockedConfiguration).addAnnotatedClass(User.class);
        verify(mockedConfiguration).addAnnotatedClass(Rol.class);
    }
}


