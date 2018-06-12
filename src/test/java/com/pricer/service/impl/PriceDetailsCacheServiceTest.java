package com.pricer.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;
import com.pricer.model.RESTMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceDetailsCacheServiceTest {

	@MockBean
	private PriceDetailsService priceDetailsService;

	PriceDetails priceDetails = new PriceDetails(6, 4500d, 3000d, 6000d, 5000d, 5);

	@Autowired
	private PriceDetailsCacheService priceDetailsCacheService;

	@Test
	public final void testGetPriceDetails() {
		JSONResponse<PriceDetails> priceDetailsJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK,
				priceDetails);
		Mockito.when(priceDetailsService.getPriceDetails(Mockito.anyInt())).thenReturn(priceDetailsJsonResponse);
		final PriceDetails response = priceDetailsCacheService.getPriceDetails(1);
		assertNotNull(response);
		assertTrue(response.getProductId().equals(6));
		assertTrue(response.getAverageStorePrice().equals(4500d));
	}

	@Test
	public final void testPutPriceDetails() {
		final PriceDetails response = priceDetailsCacheService.putPriceDetails(1, priceDetails);
		assertNotNull(response);
		assertTrue(response.getProductId().equals(6));
		assertTrue(response.getAverageStorePrice().equals(4500d));
	}

	@Test
	public final void testGetPriceDetailsResponse() {
		JSONResponse<PriceDetails> priceDetailsJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK,
				priceDetails);
		Mockito.when(priceDetailsService.getPriceDetails(Mockito.anyInt())).thenReturn(priceDetailsJsonResponse);
		final JSONResponse<PriceDetails> response = priceDetailsCacheService.getPriceDetailsResponse(1);
		assertNotNull(response);
		assertNotNull(response.getPayload());

		assertTrue(response.getPayload().getProductId().equals(6));
		assertTrue(response.getPayload().getAverageStorePrice().equals(4500d));
	}
}
