package com.uapa.software.services;

import java.util.List;

import com.uapa.software.repositories.GenericRepository;

public class GenericService<Entity> implements ICRUD<Entity> {

	private GenericRepository<Entity> genericRepository = new GenericRepository<Entity>();

	@Override
	public Entity save(Entity entity) {
		return genericRepository.saveEntity(entity);
	}

	@Override
	public boolean update(Entity entity) {
		return genericRepository.updateEntity(entity);
	}

	@Override
	public boolean delete(Entity entity) {
		return genericRepository.deleteEntity(entity);
	}

	@Override
	public Entity getById(String className, int id) {
		return genericRepository.getEntityById(className, id);
	}

	@Override
	public List<Entity> list(String className) {
		return genericRepository.getEntities(className);
	}

}
