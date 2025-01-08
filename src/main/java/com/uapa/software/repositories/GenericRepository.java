package com.uapa.software.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericRepository<Entity> implements IRepository<Entity> {
    private static final Logger logger = LoggerFactory.getLogger(GenericRepository.class);

    private Session session;
    private Transaction transaction;

    @Override
    public Entity saveEntity(Entity entity) {
        if (entity == null) {
            logger.error("Entity cannot be null");
            return null;
        }
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception ex) {
            rollbackTransaction();
            logger.error("Error saving entity: {}", ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public boolean updateEntity(Entity entity) {
        if (entity == null) {
            logger.error("Entity cannot be null");
            return false;
        }
        try {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            rollbackTransaction();
            logger.error("Error updating entity: {}", ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public boolean deleteEntity(Entity entity) {
        if (entity == null) {
            logger.error("Entity cannot be null");
            return false;
        }
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            rollbackTransaction();
            logger.error("Error deleting entity: {}", ex.getMessage(), ex);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Entity getEntityById(String className, int id) {
        try {
            transaction = session.beginTransaction();
            Entity entity = (Entity) session.get(Class.forName(className), id);
            transaction.commit();
            return entity;
        } catch (ClassNotFoundException ex) {
            logger.error("Class not found: {}", className, ex);
            throw new RuntimeException("Invalid class name: " + className, ex); // Rethrow as RuntimeException
        } catch (Exception ex) {
            rollbackTransaction();
            logger.error("Error fetching entity by ID: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error fetching entity by ID", ex); // Optional, for other exceptions
        }
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<Entity> getEntities(String className) {
        List<Entity> entities = new ArrayList<>();
        try {
            Class<?> clazz = Class.forName(className);
            Query<?> query = session.createQuery("FROM " + clazz.getSimpleName(), clazz);
            for (Object obj : query.list()) {
                entities.add((Entity) obj);
            }
        } catch (ClassNotFoundException ex) {
            logger.error("Class not found: {}", className, ex);
        } catch (Exception ex) {
            logger.error("Error fetching entities: {}", ex.getMessage(), ex);
        }
        return entities;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private void rollbackTransaction() {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
