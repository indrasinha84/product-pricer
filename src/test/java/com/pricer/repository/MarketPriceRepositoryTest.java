package com.pricer.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
