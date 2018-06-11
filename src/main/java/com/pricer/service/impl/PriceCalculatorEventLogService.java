package com.pricer.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.batch.core.JobManagerService;
import com.pricer.model.EventType;
import com.pricer.model.JSONResponse;
import com.pricer.model.JobStatus;
import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.model.RESTMessage;
import com.pricer.model.SchedulerResponse;
import com.pricer.repository.MarketPriceRepository;
import com.pricer.repository.PriceCalculatorEventLogRepository;
import com.pricer.rest.exception.SchedulerLoggingException;

@Service
public class PriceCalculatorEventLogService
		extends AbstractCRUDDataAccessService<PriceCalculatorEventLog, Integer, PriceCalculatorEventLogRepository> {

	private static Logger LOGGER = LoggerFactory.getLogger(PriceCalculatorEventLogService.class);

	@Autowired
	@Qualifier("defaultPricingJobManager")
	JobManagerService jobManager;

	@Autowired
	MarketPriceRepository marketPriceRepository;

	@Override
	protected void setKey(PriceCalculatorEventLog request, Integer key) {
		request.setId(key);
	}

	public JSONResponse<SchedulerResponse> createEvent(EventType eventType) {
		try {
			PriceCalculatorEventLog request = new PriceCalculatorEventLog();
			SchedulerResponse responseEntity = new SchedulerResponse();

			Integer startPosition = null;
			if (EventType.FULL.equals(eventType)) {
				startPosition = 1;

			} else {
				Integer maxLoggedValue = repository.getMaxLoggedPosition();
				startPosition = maxLoggedValue == null ? 1 : maxLoggedValue + 1;
			}
			Integer endPosition = marketPriceRepository.getMaxPriceCollected();
			endPosition = endPosition == null ? 0 : endPosition;
			if (startPosition > endPosition) {
				responseEntity.setJob("Nothing to run.");
				responseEntity.setStarted(new Date());
				return new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, responseEntity);
			}
			request.setStartPosition(startPosition);
			request.setEndPosition(endPosition);
			request.setEventType(eventType);
			request.setStatus(JobStatus.REQUESTED);
			JSONResponse<PriceCalculatorEventLog> response = addEntity(request);
			if (response != null && response.getStatus() != null && HttpStatus.OK.value() == response.getStatus()
					&& response.getPayload() != null && response.getPayload().getRequestedDate() != null) {
				jobManager.publishEventToQueue(response.getPayload());
				responseEntity.setJob(response.getPayload().getId().toString());
				responseEntity.setStarted(response.getPayload().getRequestedDate());
				return new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, responseEntity);
			} else {
				LOGGER.error("Price Calculation Scheduler Logging Failed." + response);
				throw new SchedulerLoggingException();
			}
		} catch (Exception e) {
			LOGGER.error("Price Calculation Scheduler Logging Failed.", e);
			throw new SchedulerLoggingException();
		}

	}

	public List<PriceCalculatorEventLog> getPendingTheadsInOrder() {
		return repository.getPendingTheadsInOrder();
	}

	public void updateEventLogOnCompletion(PriceCalculatorEventLog eventLog, Integer chunkStartPosition,
			Integer chunkEndPosition) {
		eventLog.setRestartPosition(chunkEndPosition);
		if (chunkEndPosition >= eventLog.getEndPosition()) {
			eventLog.setStatus(JobStatus.COMPLETED);
		}
		repository.save(eventLog);
	}

	public void updateEventLogStatus(PriceCalculatorEventLog eventLog, JobStatus jobStatus) {
		eventLog.setStatus(jobStatus);
		repository.save(eventLog);
	}
}
