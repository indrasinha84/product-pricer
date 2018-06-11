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
import com.pricer.model.Product;
import com.pricer.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@MockBean
	private ProductRepository productRepository;
	private final List<Product> products = new ArrayList<>();

	private Product product = new Product();

	@Autowired
	private ProductService productService;

	@Test
	public final void testAddEntity() {
		Mockito.when(productRepository.save(product))
				.thenReturn(new Product(1, "Test Product", "Test Product Descrption", 5000d));
		final JSONResponse<Product> response = productService.addEntity(product);
		assertNotNull(response);
		assertTrue(response.getPayload().getName().equals("Test Product"));
		assertTrue(response.getPayload().getDescription().equals("Test Product Descrption"));
	}

	@Test
	public final void testPutEntity() {
		Mockito.when(productRepository.save(product))
				.thenReturn(new Product(1, "Test Product", "Test Product Descrption", 5000d));
		final JSONResponse<Product> response = productService.putEntity(product, 1);
		assertNotNull(response);
		assertTrue(response.getPayload().getName().equals("Test Product"));
		assertTrue(response.getPayload().getDescription().equals("Test Product Descrption"));
	}

	@Test
	public final void testFindEntity() {
		Mockito.when(productRepository.findById(Mockito.anyInt()))
				.thenReturn(Optional.of(new Product(1, "Test Product", "Test Product Descrption", 5000d)));
		final JSONResponse<Product> response = productService.findEntity(1);
		assertNotNull(response);
		assertTrue(response.getPayload().getName().equals("Test Product"));
		assertTrue(response.getPayload().getDescription().equals("Test Product Descrption"));
	}

	@Test
	public final void testListEntities() {
		products.add(new Product());
		products.add(new Product());
		Mockito.when(productRepository.findAll()).thenReturn(products);
		JSONResponse<List<Product>> response = productService.listEntities();
		assertNotNull(response);
		assertTrue(response.getPayload().size() == 2);
	}

	@Test
	public final void testDeleteEntity() {
		Mockito.when(productRepository.findById(Mockito.anyInt()))
				.thenReturn(Optional.of(new Product(1, "Test Product", "Test Product Descrption", 5000d)));
		JSONResponse<String> response = productService.deleteEntity(1);
		assertNotNull(response);
		assertTrue(response.getPayload().equals(""));
	}

}
