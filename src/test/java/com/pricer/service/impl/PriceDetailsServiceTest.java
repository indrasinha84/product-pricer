package com.pricer.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;
import com.pricer.repository.PriceDetailsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceDetailsServiceTest {

	@MockBean
	private PriceDetailsRepository priceDetailsRepository;

	private final List<PriceDetails> priceDetailsList = new ArrayList<>();

	@Autowired
	private PriceDetailsService priceDetailsService;

	@Test
	public final void testGetPriceDetails() {
		Example<PriceDetails> example = Example.of(new PriceDetails(6, 4500d, 3000d, 6000d, 5000d, 5));

		Mockito.when(priceDetailsRepository.findOne(Mockito.any(example.getClass())))
				.thenReturn(Optional.of(new PriceDetails(6, 4500d, 3000d, 6000d, 5000d, 5)));
		final JSONResponse<PriceDetails> response = priceDetailsService.getPriceDetails(1);
		assertNotNull(response);
		assertNotNull(response.getPayload());
		assertTrue(response.getPayload().getProductId().equals(6));
		assertTrue(response.getPayload().getAverageStorePrice().equals(4500d));
	}

	@Test
	public final void testAddOrReplacePriceDetails() {
		priceDetailsList.add(new PriceDetails(6, 4500d, 3000d, 6000d, 5000d, 5));
		priceDetailsList.add(new PriceDetails(7, 4500d, 3000d, 6000d, 5000d, 5));
		Example<PriceDetails> example = Example.of(new PriceDetails());
		Mockito.when(priceDetailsRepository.findOne(Mockito.any(example.getClass()))).thenReturn(Optional.of(new PriceDetails()));
		Mockito.when(priceDetailsRepository.saveAll(Mockito.any(priceDetailsList.getClass()))).thenReturn(priceDetailsList);
		
		final List<PriceDetails> response = priceDetailsService.addOrReplacePriceDetails(priceDetailsList);
		assertNotNull(response);
		assertTrue(response.size() == 2);
	}

}
