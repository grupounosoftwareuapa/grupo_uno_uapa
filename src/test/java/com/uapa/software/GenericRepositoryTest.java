package com.uapa.software;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uapa.software.repositories.GenericRepository;

class GenericRepositoryTest {

    private GenericRepository<Object> repository;
    private Session sessionMock;
    private Transaction transactionMock;

    @BeforeEach
    void setUp() {
        sessionMock = mock(Session.class);
        transactionMock = mock(Transaction.class);
        repository = new GenericRepository<>();
        repository.setSession(sessionMock);
    }

    @Test
    void testSaveEntity_Success() {
        Object entity = new Object();
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);

        Object result = repository.saveEntity(entity);

        verify(sessionMock).persist(entity);
        verify(transactionMock).commit();
        assertNotNull(result, "Entity should be saved and returned");
    }

    @Test
    void testSaveEntity_NullEntity() {
        Object result = repository.saveEntity(null);

        verify(sessionMock, never()).beginTransaction();
        assertNull(result, "Saving a null entity should return null");
    }

    @Test
    void testSaveEntity_Exception() {
        Object entity = new Object();
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);
        doThrow(new RuntimeException("Error")).when(sessionMock).persist(entity);

        Object result = repository.saveEntity(entity);

        verify(transactionMock).rollback();
        assertNull(result, "Entity should not be saved in case of an exception");
    }

    @Test
    void testUpdateEntity_Success() {
        Object entity = new Object();
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);

        boolean result = repository.updateEntity(entity);

        verify(sessionMock).merge(entity);
        verify(transactionMock).commit();
        assertTrue(result, "Entity should be updated successfully");
    }

    @Test
    void testUpdateEntity_NullEntity() {
        boolean result = repository.updateEntity(null);

        verify(sessionMock, never()).beginTransaction();
        assertFalse(result, "Updating a null entity should return false");
    }

    @Test
    void testUpdateEntity_Exception() {
        Object entity = new Object();
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);
        doThrow(new RuntimeException("Error")).when(sessionMock).merge(entity);

        boolean result = repository.updateEntity(entity);

        verify(transactionMock).rollback();
        assertFalse(result, "Entity should not be updated in case of an exception");
    }

    @Test
    void testDeleteEntity_Success() {
        Object entity = new Object();
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);

        boolean result = repository.deleteEntity(entity);

        verify(sessionMock).remove(entity);
        verify(transactionMock).commit();
        assertTrue(result, "Entity should be deleted successfully");
    }

    @Test
    void testDeleteEntity_NullEntity() {
        boolean result = repository.deleteEntity(null);

        verify(sessionMock, never()).beginTransaction();
        assertFalse(result, "Deleting a null entity should return false");
    }

    @Test
    void testDeleteEntity_Exception() {
        Object entity = new Object();
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);
        doThrow(new RuntimeException("Error")).when(sessionMock).remove(entity);

        boolean result = repository.deleteEntity(entity);

        verify(transactionMock).rollback();
        assertFalse(result, "Entity should not be deleted in case of an exception");
    }

    @Test
    void testGetEntityById_Success() throws ClassNotFoundException {
        Object entity = new Object();
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);
        when(sessionMock.get(Object.class, 1)).thenReturn(entity);

        Object result = repository.getEntityById("java.lang.Object", 1);

        verify(transactionMock).commit();
        assertNotNull(result, "Entity should be fetched successfully");
    }

    @Test
    void testGetEntityById_InvalidClass() {
        assertThrows(RuntimeException.class, () -> {
            repository.getEntityById("InvalidClass", 1);
        }, "Invalid class should throw a RuntimeException");
    }

    @Test
    void testGetEntities_Success() throws ClassNotFoundException {
        List<Object> entities = new ArrayList<>();
        entities.add(new Object());
        Query<Object> queryMock = mock(Query.class);

        when(sessionMock.createQuery(anyString(), eq(Object.class))).thenReturn(queryMock);
        when(queryMock.list()).thenReturn(entities);

        List<Object> result = repository.getEntities("java.lang.Object");

        assertNotNull(result, "Result list should not be null");
        assertEquals(1, result.size(), "Entities list size should match");
    }

    @Test
    void testGetEntities_InvalidClass() {
        List<Object> result = repository.getEntities("InvalidClass");

        assertNotNull(result, "Result list should not be null");
        assertTrue(result.isEmpty(), "Entities list should be empty for an invalid class");
    }
}

