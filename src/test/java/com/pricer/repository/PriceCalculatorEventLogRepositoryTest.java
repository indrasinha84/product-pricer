package com.pricer.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.EventType;
import com.pricer.model.JobStatus;
import com.pricer.model.PriceCalculatorEventLog;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
public class PriceCalculatorEventLogRepositoryTest {

	@Autowired
	PriceCalculatorEventLogRepository repository;

	@Test
	public final void testGetMaxLoggedPosition() {
		final Integer count = repository.getMaxLoggedPosition();
		assertNotNull(count);
		assertTrue(count != 0);	}

	@Test
	public final void testGetPendingTheadsInOrder() {
		List<PriceCalculatorEventLog> list = repository.getPendingTheadsInOrder();
		assertNotNull(list);
		assertTrue(list.size() != 0);

	}

	@Test
	public final void testSavePriceCalculatorEventLog() {
		PriceCalculatorEventLog ent = new PriceCalculatorEventLog(1, 1, new Date(), null, null, JobStatus.REQUESTED,
				null, EventType.ADHOC);
		final PriceCalculatorEventLog log = repository.save(ent);
		assertNotNull(log);
	}

}
