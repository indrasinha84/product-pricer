package com.pricer.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.Store;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
public class StoreRepositoryTest {

	@Autowired
	StoreRepository storeRepository;

	@Test
	public final void testFindAll() {
		final List<Store> store = storeRepository.findAll();
		assertNotNull(store);
		assertTrue(store.size() != 0);
	}

	@Test
	public final void testSave() {
		Store store = new Store("Test Store", "Test Store Descrption");
		final Store result = storeRepository.save(store);
		assertNotNull(result);
	}

	@Test
	public final void testFindOne() {
		Store ent = new Store();
		ent.setId(1);
		Example<Store> example = Example.of(ent);
		final Optional<Store> store = storeRepository.findOne(example);
		assertNotNull(store.get());
	}

	@Test
	public final void testFindById() {
		final Optional<Store> store = storeRepository.findById(1);
		assertNotNull(store.get());
	}

}
