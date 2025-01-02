package com.uapa.software;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uapa.software.models.Rol;
import com.uapa.software.models.User;
import com.uapa.software.repositories.GenericRepository;
import com.uapa.software.utils.HibernateTestUtil;

class GenericRepositoryTest {

	private GenericRepository<User> repository;
    private Session session;

    @BeforeEach
    void setUp() {
        session = HibernateTestUtil.getSessionFactory().openSession();
        repository = new GenericRepository<>();
        repository.setSession(session);

        // Insertar datos de prueba dentro de una transacción
        Transaction transaction = session.beginTransaction();
        User entity = new User();
        entity.setPassword("test123");
        entity.setUserName("test123");

        Rol rol = new Rol();
        rol.setName("Admin");
        List<Rol> roles = new ArrayList<>();
        roles.add(rol);
        entity.setRoles(roles);

        session.persist(rol); // Primero persiste el Rol
        session.persist(entity);
        transaction.commit();
    }

    @AfterEach
    void tearDown() {
        if (session != null) {
            session.close();
        }
    }


    @Test
    void testGetEntities() {
        List<User> entities = repository.getEntities(User.class.getName());
        assertNotNull(entities);
        assertFalse(entities.isEmpty());
        assertEquals(1, entities.size());
    }

//    @Test
//    void testUpdateEntity() {
//        Object entity = new Object();
//
//        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
//
//        boolean result = repository.updateEntity(entity);
//
//        verify(mockSession).merge(entity);
//        verify(mockTransaction).commit();
//        assertTrue(result);
//    }
//
//    @Test
//    void testDeleteEntity() {
//        Object entity = new Object();
//
//        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
//
//        boolean result = repository.deleteEntity(entity);
//
//        verify(mockSession).remove(entity);
//        verify(mockTransaction).commit();
//        assertTrue(result);
//    }
//
//    @Test
//    void testGetEntityById() {
//        String className = "com.example.Entity";
//        int id = 1;
//        Object entity = new Object();
//
//        when(mockSession.beginTransaction()).thenReturn(mockTransaction);
//        when(mockSession.get(className, id)).thenReturn(entity);
//
//        Object result = repository.getEntityById(className, id);
//
//        verify(mockSession).get(className, id);
//        assertEquals(entity, result);
//    }
//
//    @Test
//    void testGetEntities() throws ClassNotFoundException {
//        String className = "com.example.Entity";
//        List<Object> mockList = new ArrayList<>();
//        mockList.add(new Object());
//
//        // Declarar el mock con el tipo correcto
//        Query<Object> mockQuery = mock(Query.class);
//
//        // Configurar los mocks para que coincidan los tipos
//        when(mockSession.createQuery(anyString(), eq(Object.class))).thenReturn(mockQuery);
//        when(mockQuery.list()).thenReturn(mockList);
//
//        // Llamar al método a probar
//        List<Object> result = repository.getEntities(className);
//
//        // Verificar las interacciones
//        verify(mockSession).createQuery(anyString(), eq(Object.class));
//        assertEquals(mockList.size(), result.size());
//    }

}
