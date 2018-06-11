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

import com.pricer.model.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
public class ProductRepositoryTest {

	@Autowired
	ProductRepository productRepository;

	@Test
	public final void testGetProductsForPriceCalculation() {
		final List<Product> list = productRepository.getProductsForPriceCalculation(1, 1);
		assertNotNull(list);
		assertTrue(list.size() != 0);
	}

	@Test
	public final void testFindAll() {
		final List<Product> product = productRepository.findAll();
		assertNotNull(product);
		assertTrue(product.size() != 0);
	}

	@Test
	public final void testSave() {
		Product product = new Product("Test Product", "Test Product Descrption", 50000d);
		final Product result = productRepository.save(product);
		assertNotNull(result);
	}

	@Test
	public final void testFindOne() {
		Product ent = new Product();
		ent.setId(1);
		Example<Product> example = Example.of(ent);
		final Optional<Product> product = productRepository.findOne(example);
		assertNotNull(product.get());
	}

	@Test
	public final void testFindById() {
		final Optional<Product> product = productRepository.findById(1);
		assertNotNull(product.get());
	}

}
