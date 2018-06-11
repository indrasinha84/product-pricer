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

import com.pricer.model.EffectiveStatus;
import com.pricer.model.MarketPrice;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
public class MarketPriceRepositoryTest {

	@Autowired
	MarketPriceRepository marketPriceRepository;

	@Test
	public final void testGetMaxPriceCollected() {
		final Integer count = marketPriceRepository.getMaxPriceCollected();
		assertNotNull(count);
		assertTrue(count == 1);
	}

	@Test
	public void testSaveMarketPrice() throws Exception {
		MarketPrice ent = new MarketPrice(1, 1, "Test Price", 5666.66);
		ent.setEffectiveStatus(EffectiveStatus.ACTIVE);
		final MarketPrice marketPrice = marketPriceRepository.save(ent);
		assertNotNull(marketPrice);
	}

	@Test
	public void testFindOneMarketPrice() throws Exception {
		MarketPrice ent = new MarketPrice();
		ent.setStoreId(1);
		ent.setProductId(1);
		ent.setEffectiveStatus(EffectiveStatus.ACTIVE);
		Example<MarketPrice> example = Example.of(ent);
		final Optional<MarketPrice> marketPrice = marketPriceRepository.findOne(example);
		assertNotNull(marketPrice.get());
	}

	@Test
	public void testFindAllMarketPrice() throws Exception {
		final List<MarketPrice> marketPrice = marketPriceRepository.findAll();
		assertNotNull(marketPrice);
		assertTrue(marketPrice.size() == 1);
	}

	@Test
	public void testCountMarketPrice() throws Exception {
		MarketPrice ent = new MarketPrice();
		ent.setStoreId(1);
		ent.setProductId(1);
		ent.setEffectiveStatus(EffectiveStatus.ACTIVE);
		Example<MarketPrice> example = Example.of(ent);
		final long count = marketPriceRepository.count(example);
		assertNotNull(count);
		assertTrue(count == 1);
	}

	@Test
	public void testFindByIdMarketPrice() throws Exception {
		final Optional<MarketPrice> marketPrice = marketPriceRepository.findById(1);
		assertNotNull(marketPrice.get());
	}
}
