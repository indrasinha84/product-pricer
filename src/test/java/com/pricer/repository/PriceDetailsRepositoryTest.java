package com.pricer.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import com.pricer.model.PriceDetails;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
public class PriceDetailsRepositoryTest {

	@Autowired
	PriceDetailsRepository priceDetailsRepository;

	@Test
	public final void testFindAll() {
		final List<PriceDetails> priceDetails = priceDetailsRepository.findAll();
		assertNotNull(priceDetails);
		assertTrue(priceDetails.size() != 0);
	}

	@Test
	public final void testSave() {
		PriceDetails priceDetails = new PriceDetails(1, 4500d, 3000d, 6000d, 5000d, 5);
		priceDetails.setEffectiveStatus(EffectiveStatus.ACTIVE);
		final PriceDetails result = priceDetailsRepository.save(priceDetails);
		assertNotNull(result);
	}

	@Test
	public final void testSaveAllIterableOfS() {
		PriceDetails priceDetails1 = new PriceDetails(1, 4500d, 3000d, 6000d, 5000d, 5);
		PriceDetails priceDetails2 = new PriceDetails(1, 4500d, 3000d, 6000d, 5000d, 5);

		priceDetails1.setEffectiveStatus(EffectiveStatus.ACTIVE);
		priceDetails2.setEffectiveStatus(EffectiveStatus.INACTIVE);
		List<PriceDetails> list = new ArrayList<>();
		list.add(priceDetails1);
		list.add(priceDetails2);
		final List<PriceDetails> results = priceDetailsRepository.saveAll(list);
		assertNotNull(results);
		assertTrue(results.size() == 2);

	}

	@Test
	public final void testFindOne() {
		PriceDetails ent = new PriceDetails();
		ent.setProductId(1);
		ent.setEffectiveStatus(EffectiveStatus.ACTIVE);
		Example<PriceDetails> example = Example.of(ent);
		final Optional<PriceDetails> priceDetails = priceDetailsRepository.findOne(example);
		assertNotNull(priceDetails.get());
	}

}
