package com.pricer.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

import com.pricer.model.JSONResponse;
import com.pricer.model.MarketPrice;
import com.pricer.model.RESTMessage;
import com.pricer.service.impl.MarketPriceService;

@RunWith(SpringRunner.class)
@WebMvcTest(MarketPriceController.class)
public class MarketPriceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	MarketPriceService marketPriceService;

	MarketPrice marketPrice = new MarketPrice(5, 6, "Test Price", 5666.66);

	JSONResponse<MarketPrice> marketPriceJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, marketPrice);

	String testJson = "{\"store\":5,\"product\":6,\"price\":5666.66,\"notes\":\"Test Price\"}";

	@Test
	public final void testAddMarketPrice() throws Exception {
		Mockito.when(marketPriceService.addEntity(Mockito.any(MarketPrice.class))).thenReturn(marketPriceJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/store/product")
				.accept(MediaType.APPLICATION_JSON).content(testJson).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_UTF8, MediaType.valueOf(response.getHeader(HttpHeaders.CONTENT_TYPE)));
		String content = "{\"status\":200,\"message\":\"success\",\"payload\":{\"store\":5,\"product\":6,\"price\":5666.66,\"created\":null}}";
		assertEquals(content, response.getContentAsString());

	}

	@Test
	public final void testPutMarketPrice() throws Exception {
		Mockito.when(marketPriceService.putEntity(Mockito.any(MarketPrice.class), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(marketPriceJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/store/product/5/6")
				.accept(MediaType.APPLICATION_JSON).content(testJson).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_UTF8, MediaType.valueOf(response.getHeader(HttpHeaders.CONTENT_TYPE)));
		String content = "{\"status\":200,\"message\":\"success\",\"payload\":{\"store\":5,\"product\":6,\"price\":5666.66,\"created\":null}}";
		assertEquals(content, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testRetrieveMarketPrice() throws Exception {
		Mockito.when(marketPriceService.getMarketPrice(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(marketPriceJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/store/product/5/6");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":{\"store\":5,\"product\":6,\"price\":5666.66,\"created\":null}}";
		assertEquals(expected, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testListMarketPrices() throws Exception {
		List<MarketPrice> list = new ArrayList<>(2);
		list.add(marketPrice);
		list.add(marketPrice);
		JSONResponse<List<MarketPrice>> marketPriceListJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK,
				list);
		Mockito.when(marketPriceService.listEntities()).thenReturn(marketPriceListJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/store/product");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":[{\"store\":5,\"product\":6,\"price\":5666.66,\"created\":null},{\"store\":5,\"product\":6,\"price\":5666.66,\"created\":null}]}";
		assertEquals(expected, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public final void testDeleteMarketPrice() throws Exception {
		JSONResponse<String> deleteResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
		Mockito.when(marketPriceService.deleteMarketPrice(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(deleteResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/store/product/5/6");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":\"\"}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, response.getContentAsString());
	}

}
