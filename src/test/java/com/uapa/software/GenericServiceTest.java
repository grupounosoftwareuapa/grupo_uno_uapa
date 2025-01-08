package com.uapa.software;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.uapa.software.repositories.GenericRepository;
import com.uapa.software.services.GenericService;

class GenericServiceTest {

    @Mock
    private GenericRepository<Object> mockGenericRepository;

    @InjectMocks
    private GenericService<Object> genericService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Object entity = new Object();
        when(mockGenericRepository.saveEntity(entity)).thenReturn(entity);

        Object result = genericService.save(entity);

        assertEquals(entity, result);
        verify(mockGenericRepository, times(1)).saveEntity(entity);
    }

    @Test
    void testUpdate() {
        Object entity = new Object();
        when(mockGenericRepository.updateEntity(entity)).thenReturn(true);

        boolean result = genericService.update(entity);

        assertTrue(result);
        verify(mockGenericRepository, times(1)).updateEntity(entity);
    }

    @Test
    void testDelete() {
        Object entity = new Object();
        when(mockGenericRepository.deleteEntity(entity)).thenReturn(true);

        boolean result = genericService.delete(entity);

        assertTrue(result);
        verify(mockGenericRepository, times(1)).deleteEntity(entity);
    }

    @Test
    void testGetById() {
        String className = "TestEntity";
        int id = 1;
        Object entity = new Object();
        when(mockGenericRepository.getEntityById(className, id)).thenReturn(entity);

        Object result = genericService.getById(className, id);

        assertEquals(entity, result);
        verify(mockGenericRepository, times(1)).getEntityById(className, id);
    }

    @Test
    void testList() {
        String className = "TestEntity";
        List<Object> entities = Arrays.asList(new Object(), new Object());
        when(mockGenericRepository.getEntities(className)).thenReturn(entities);

        List<Object> result = genericService.list(className);

        assertEquals(entities, result);
        verify(mockGenericRepository, times(1)).getEntities(className);
    }
}
