package com.pricer.service.impl;

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

import com.pricer.batch.core.JobManagerService;
import com.pricer.model.EventType;
import com.pricer.model.JSONResponse;
import com.pricer.model.JobStatus;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.SchedulerResponse;
import com.pricer.repository.MarketPriceRepository;
import com.pricer.repository.PriceCalculatorEventLogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceCalculatorEventLogServiceTest {

	@MockBean
	private MarketPriceRepository marketPriceRepository;

	@MockBean
	PriceCalculatorEventLogRepository repository;

	@MockBean
	JobManagerService jobManager;

	@Autowired
	private PriceCalculatorEventLogService priceCalculatorEventLogService;

	@Test
	public final void testCreateEvent() {
		Mockito.when(repository.getMaxLoggedPosition()).thenReturn(6);
		Mockito.when(marketPriceRepository.getMaxPriceCollected()).thenReturn(10);
		Mockito.when(repository.save(Mockito.any(PriceCalculatorEventLog.class)))
				.thenReturn(new PriceCalculatorEventLog(1, 1, 1, new Date(), null, null, JobStatus.REQUESTED, null,
						EventType.ADHOC));
		final JSONResponse<SchedulerResponse> response = priceCalculatorEventLogService.createEvent(EventType.ADHOC);
		assertNotNull(response);

	}

	@Test
	public final void testGetPendingTheadsInOrder() {
		List<PriceCalculatorEventLog> list = new LinkedList<>();
		list.add(new PriceCalculatorEventLog(1, 1, 1, new Date(), null, null, JobStatus.REQUESTED, null,
				EventType.ADHOC));
		list.add(new PriceCalculatorEventLog(2, 1, 1, new Date(), null, null, JobStatus.REQUESTED, null,
				EventType.ADHOC));
		Mockito.when(repository.getPendingTheadsInOrder()).thenReturn(list);
		List<PriceCalculatorEventLog> result = priceCalculatorEventLogService.getPendingTheadsInOrder();
		assertNotNull(result);
		assertTrue(result.size() == 2);
	}
}
