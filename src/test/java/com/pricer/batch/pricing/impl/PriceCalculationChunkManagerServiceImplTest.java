package com.pricer.batch.pricing.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.EventType;
import com.pricer.model.JobStatus;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.Product;
import com.pricer.service.impl.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceCalculationChunkManagerServiceImplTest {

	@MockBean
	ProductService productService;

	@Autowired
	PriceCalculationChunkManagerServiceImpl priceCalculationChunkManagerService;

	@Test
	public final void testRead() {
		Product product1 = new Product(1, "Test Product", "Test Description", 5000d);
		Product product2 = new Product(2, "Test Product 2", "Test Description", 5600d);
		List<Product> list = new LinkedList<>();
		list.add(product1);
		list.add(product2);
		PriceCalculatorEventLog eventLog = new PriceCalculatorEventLog(1, 1, 10, new Date(), null, null,
				JobStatus.REQUESTED, null, EventType.ADHOC);

		Mockito.when(productService.getProductsForPriceCalculation(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(list);
		List<Product>  response = priceCalculationChunkManagerService.read(eventLog, 1, 1);
		assertNotNull(response);
		assertTrue(response.size() == 2);
	}

}
