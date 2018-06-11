package com.pricer.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.JSONResponse;
import com.pricer.model.Store;
import com.pricer.repository.StoreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreServiceTest {

	@MockBean
	private StoreRepository storeRepository;
	private final List<Store> stores = new ArrayList<>();

	private Store store = new Store();

	@Autowired
	private StoreService storeService;

	@Test
	public final void testAddEntity() {
		Mockito.when(storeRepository.save(store)).thenReturn(new Store("Test Store", "Test Store Descrption"));
		final JSONResponse<Store> response = storeService.addEntity(store);
		assertNotNull(response);
		assertTrue(response.getPayload().getName().equals("Test Store"));
		assertTrue(response.getPayload().getDescription().equals("Test Store Descrption"));
	}

	@Test
	public final void testPutEntity() {
		Mockito.when(storeRepository.save(store)).thenReturn(new Store("Test Store", "Test Store Descrption"));
		final JSONResponse<Store> response = storeService.putEntity(store, 1);
		assertNotNull(response);
		assertTrue(response.getPayload().getName().equals("Test Store"));
		assertTrue(response.getPayload().getDescription().equals("Test Store Descrption"));
	}

	@Test
	public final void testFindEntity() {
		Mockito.when(storeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Store("Test Store", "Test Store Descrption")));		
		final JSONResponse<Store> response = storeService.findEntity(1);
		assertNotNull(response);
		assertTrue(response.getPayload().getName().equals("Test Store"));
		assertTrue(response.getPayload().getDescription().equals("Test Store Descrption"));
	}

	@Test
	public final void testListEntities() {
		stores.add(new Store());
		stores.add(new Store());
		Mockito.when(storeRepository.findAll()).thenReturn(stores);
		JSONResponse<List<Store>> response = storeService.listEntities();
		assertNotNull(response);
		assertTrue(response.getPayload().size() == 2);
	}

	@Test
	public final void testDeleteEntity() {
		Mockito.when(storeRepository.findById(Mockito.anyInt()))
				.thenReturn(Optional.of(new Store("Test Store", "Test Store Descrption")));
		JSONResponse<String> response = storeService.deleteEntity(1);
		assertNotNull(response);
		assertTrue(response.getPayload().equals(""));
	}

}
