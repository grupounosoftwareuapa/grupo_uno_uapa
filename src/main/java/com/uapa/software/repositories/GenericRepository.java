package com.uapa.software.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class GenericRepository<Entity> implements IRepository<Entity> {

	Session session = null;// HibernateUtil.getSessionFactory().openSession();
	Transaction transaction = null;

	@Override
	public Entity saveEntity(Entity entity) {

		try {
			transaction = session.beginTransaction();
			session.persist(entity);
			transaction.commit();

			return entity;

		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error when creating entity:: " + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean updateEntity(Entity entity) {
		try {
			transaction = session.beginTransaction();
			session.merge(entity);
			transaction.commit();

			return true;

		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error when creating entity:: " + ex.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteEntity(Entity entity) {
		try {
			transaction = session.beginTransaction();
			session.remove(entity);
			transaction.commit();

			return true;

		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error when creating entity:: " + ex.getMessage());
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity getEntityById(String className, int id) {
		try {
			transaction = session.beginTransaction();
			return (Entity) session.get(className, id);

		} catch (Exception ex) {
			System.out.println("Error when creating entity:: " + ex.getMessage());
		}
		return null;
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

		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + className);
		} catch (Exception ex) {
			System.out.println("Error when creating entity:: " + ex.getMessage());
		}
		return entities;

	}

	public void setSession(Session session) {
		this.session = session;
	}

}
