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
import com.pricer.model.MarketPrice;
import com.pricer.model.MarketPrice;
import com.pricer.repository.MarketPriceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarketPriceServiceTest {

	@MockBean
	private MarketPriceRepository marketPriceRepository;
	private final List<MarketPrice> marketPrices = new ArrayList<>();

	private MarketPrice marketPrice = new MarketPrice(5, 6, "Test Price", 5666.66);

	@Autowired
	private MarketPriceService marketPriceService;

	@Test
	public final void testGetMarketPrice() {

		Mockito.when(marketPriceRepository.findOne(Mockito.any(Example.of(marketPrice).getClass())))
				.thenReturn(Optional.of(new MarketPrice(5, 6, "Test Price", 5666.66)));
		final JSONResponse<MarketPrice> response = marketPriceService.getMarketPrice(1, 1);
		assertNotNull(response);
		assertTrue(response.getPayload().getNotes().equals("Test Price"));
		assertTrue(response.getPayload().getStorePrice().equals(5666.66));
	}

	@Test
	public final void testDeleteMarketPrice() {
		Mockito.when(marketPriceRepository.save(Mockito.any(MarketPrice.class)))
				.thenReturn(new MarketPrice(5, 6, "Test Price", 5666.66));

		Mockito.when(marketPriceRepository.findOne(Mockito.any(Example.of(marketPrice).getClass())))
				.thenReturn(Optional.of(new MarketPrice(5, 6, "Test Price", 5666.66)));

		JSONResponse<String> response = marketPriceService.deleteMarketPrice(1, 1);
		assertNotNull(response);
		assertTrue(response.getPayload().equals(""));
	}

	@Test
	public final void testPutEntityMarketPriceIntegerInteger() {
		Mockito.when(marketPriceRepository.save(Mockito.any(MarketPrice.class)))
				.thenReturn(new MarketPrice(5, 6, "Test Price", 5666.66));

		Mockito.when(marketPriceRepository.findOne(Mockito.any(Example.of(marketPrice).getClass())))
				.thenReturn(Optional.of(new MarketPrice(5, 6, "Test Price", 5666.66)));

		final JSONResponse<MarketPrice> response = marketPriceService.putEntity(marketPrice, 1, 1);
		assertNotNull(response);
		assertTrue(response.getPayload().getNotes().equals("Test Price"));
		assertTrue(response.getPayload().getStorePrice().equals(5666.66));
	}

	@Test
	public final void testAddEntity() {

		Mockito.when(marketPriceRepository.save(Mockito.any(MarketPrice.class)))
				.thenReturn(new MarketPrice(5, 6, "Test Price", 5666.66));

		Mockito.when(marketPriceRepository.findOne(Mockito.any(Example.of(marketPrice).getClass())))
				.thenReturn(Optional.of(new MarketPrice(5, 6, "Test Price", 5666.66)));
		final JSONResponse<MarketPrice> response = marketPriceService.addEntity(marketPrice);
		assertNotNull(response);
		assertTrue(response.getPayload().getNotes().equals("Test Price"));
		assertTrue(response.getPayload().getStorePrice().equals(5666.66));
	}

	@Test
	public final void testListEntities() {
		marketPrices.add(new MarketPrice());
		marketPrices.add(new MarketPrice());
		Mockito.when(marketPriceRepository.findAll(Mockito.any(Example.of(marketPrice).getClass())))
				.thenReturn(marketPrices);
		JSONResponse<List<MarketPrice>> response = marketPriceService.listEntities();
		assertNotNull(response);
		assertTrue(response.getPayload().size() == 2);
	}

}
