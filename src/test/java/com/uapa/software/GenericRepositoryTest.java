package com.uapa.software;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@Test
	void testGetEntityById() {
		// Insertar datos de prueba
		Transaction transaction = session.beginTransaction();
		User user = new User();
		user.setUserName("testuser");
		user.setPassword("testpassword");
		session.persist(user);
		transaction.commit();

		// Recuperar la entidad por ID
		User retrievedUser = repository.getEntityById(User.class.getName(), user.getId());

		// Verificar resultados
		assertNotNull(retrievedUser);
		assertEquals(user.getUserName(), retrievedUser.getUserName());
		assertEquals(user.getPassword(), retrievedUser.getPassword());
	}

	@Test
	void testUpdateEntity() {
		// Insertar datos de prueba
		Transaction transaction = session.beginTransaction();
		User user = new User();
		user.setUserName("testuser");
		user.setPassword("testpassword");
		session.persist(user);
		transaction.commit();

		// Actualizar la entidad
		user.setUserName("updateduser");
		user.setPassword("updatedpassword");
		boolean isUpdated = repository.updateEntity(user);

		// Verificar resultados
		assertTrue(isUpdated);

		// Verificar cambios en la base de datos
		User updatedUser = repository.getEntityById(User.class.getName(), user.getId());
		assertEquals("updateduser", updatedUser.getUserName());
		assertEquals("updatedpassword", updatedUser.getPassword());
	}

	@Test
	void testDeleteEntity() {
		// Insertar datos de prueba
		Transaction transaction = session.beginTransaction();
		User user = new User();
		user.setUserName("testuser");
		user.setPassword("testpassword");
		session.persist(user);
		transaction.commit();

		// Eliminar la entidad
		boolean isDeleted = repository.deleteEntity(user);

		// Verificar resultados
		assertTrue(isDeleted);

		// Verificar que la entidad ya no existe en la base de datos
		User deletedUser = repository.getEntityById(User.class.getName(), user.getId());
		assertNull(deletedUser);
	}

	@Test
	void testSaveEntity() {
		// Crear la entidad a guardar
		User user = new User();
		user.setUserName("testuser");
		user.setPassword("testpassword");

		// Guardar la entidad en la base de datos
		User savedUser = repository.saveEntity(user);

		// Verificar resultados
		assertNotNull(savedUser);
		assertNotNull(savedUser.getId()); // ID debe haberse generado
		assertEquals("testuser", savedUser.getUserName());
		assertEquals("testpassword", savedUser.getPassword());

		// Verificar que la entidad realmente existe en la base de datos
		User retrievedUser = repository.getEntityById(User.class.getName(), savedUser.getId());
		assertNotNull(retrievedUser);
		assertEquals(savedUser.getUserName(), retrievedUser.getUserName());
		assertEquals(savedUser.getPassword(), retrievedUser.getPassword());
	}

}
