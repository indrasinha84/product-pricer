package com.pricer.batch.pricing.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.PriceDetails;
import com.pricer.model.Product;
import com.pricer.pricing.rule.calculator.PricingCalculator;
import com.pricer.service.impl.PriceDetailsCacheService;
import com.pricer.service.impl.PriceDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceCalculationProcessorServiceImplTest {

	@Autowired
	PriceCalculationProcessorServiceImpl priceCalculationProcessorServiceImpl;

	@MockBean
	private PriceDetailsService priceDetailsService;

	@MockBean
	private PricingCalculator pricingCalculator;

	@MockBean
	PriceDetailsCacheService priceDetailsCacheService;
	PriceDetails priceDetails = new PriceDetails(6, 4500d, 3000d, 6000d, 5000d, 5);

	@Test
	public final void testProcess() throws Exception {

		Mockito.when(priceDetailsCacheService.putPriceDetails(Mockito.anyInt(), Mockito.any(PriceDetails.class)))
				.thenReturn(priceDetails);
		Mockito.when(pricingCalculator.getDetailsForAProduct(Mockito.any(Product.class))).thenReturn(priceDetails);

		List<PriceDetails> list = new ArrayList<>();
		list.add(priceDetails);
		Mockito.when(priceDetailsService.addOrReplacePriceDetails(Mockito.any(List.class))).thenReturn(list);

		Product product1 = new Product(1, "Test Product", "Test Description", 5000d);
		Product product2 = new Product(2, "Test Product 2", "Test Description", 5600d);

		Queue<Product> productQueue = new ConcurrentLinkedQueue<>();
		productQueue.offer(product1);
		productQueue.offer(product2);
		boolean result = priceCalculationProcessorServiceImpl.process(productQueue);

		assertTrue(result);

	}

}
