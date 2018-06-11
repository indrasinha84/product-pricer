package com.pricer.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pricer.model.Command;
import com.pricer.model.EventType;
import com.pricer.model.JSONResponse;
import com.pricer.model.RESTMessage;
import com.pricer.model.SchedulerResponse;
import com.pricer.service.impl.PriceCalculatorEventLogService;

@RunWith(SpringRunner.class)
@WebMvcTest(SchedulingController.class)
public class SchedulingControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	PriceCalculatorEventLogService priceCalculatorEventLogService;
	@Test
	public final void testPriceCalculator() throws Exception {
		SchedulerResponse sch = new SchedulerResponse("started", null);
		JSONResponse<SchedulerResponse> schedulingJsonResponse = 
				new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, sch );
		;
		Mockito.when(priceCalculatorEventLogService.createEvent(Mockito.any(EventType.class))).
		thenReturn(schedulingJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/jobs/pricecalculator").
				param("command", Command.START.value());
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_UTF8, MediaType.valueOf(response.getHeader(HttpHeaders.CONTENT_TYPE)));
		String content = "{\"status\":200,\"message\":\"success\",\"payload\":{\"job\":\"started\",\"started\":null}}";
		assertEquals(content, response.getContentAsString());

	}

}
