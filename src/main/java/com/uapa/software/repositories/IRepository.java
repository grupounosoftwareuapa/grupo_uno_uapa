package com.uapa.software.repositories;

import java.util.List;

public interface IRepository <T>{

	public T saveEntity(T entity);
	public boolean updateEntity(T entity);
	public boolean deleteEntity(T entity);
	public T getEntityById(String className, int id);
	public List<T> getEntities(String className);

}
