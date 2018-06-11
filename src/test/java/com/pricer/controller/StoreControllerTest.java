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
import com.pricer.model.RESTMessage;
import com.pricer.model.Store;
import com.pricer.service.impl.StoreService;

@RunWith(SpringRunner.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	StoreService storeService;

	Store store = new Store("Test Store", "Test Store Descrption");

	JSONResponse<Store> storeJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, store);

	String testJson = "{\"name\": \"Test Store\",\"description\": \"Test Store Descrption\",\"basePrice\": \"50000\"}";

	@Test
	public final void testAddStore() throws Exception {
		Mockito.when(storeService.addEntity(Mockito.any(Store.class))).thenReturn(storeJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/store").accept(MediaType.APPLICATION_JSON)
				.content(testJson).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_UTF8, MediaType.valueOf(response.getHeader(HttpHeaders.CONTENT_TYPE)));
		String content = "{\"status\":200,\"message\":\"success\",\"payload\":{\"name\":\"Test Store\",\"description\":\"Test Store Descrption\",\"created\":null,\"identifier\":null}}";
		assertEquals(content, response.getContentAsString());

	}

	@Test
	public final void testPutStore() throws Exception {
		Mockito.when(storeService.putEntity(Mockito.any(Store.class), Mockito.anyInt()))
				.thenReturn(storeJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/store/6")
				.accept(MediaType.APPLICATION_JSON).content(testJson).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_UTF8, MediaType.valueOf(response.getHeader(HttpHeaders.CONTENT_TYPE)));
		String content = "{\"status\":200,\"message\":\"success\",\"payload\":{\"name\":\"Test Store\",\"description\":\"Test Store Descrption\",\"created\":null,\"identifier\":null}}";
		assertEquals(content, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testRetriveStore() throws Exception {
		Mockito.when(storeService.findEntity(Mockito.anyInt())).thenReturn(storeJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/store/6");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":{\"name\":\"Test Store\",\"description\":\"Test Store Descrption\",\"created\":null,\"identifier\":null}}";
		assertEquals(expected, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testListStores() throws Exception {
		List<Store> list = new ArrayList<>(2);
		list.add(store);
		list.add(store);
		JSONResponse<List<Store>> storeListJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, list);
		Mockito.when(storeService.listEntities()).thenReturn(storeListJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/store");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":[{\"name\":\"Test Store\",\"description\":\"Test Store Descrption\",\"created\":null,\"identifier\":null},{\"name\":\"Test Store\",\"description\":\"Test Store Descrption\",\"created\":null,\"identifier\":null}]}";
		assertEquals(expected, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public final void testDeleteStore() throws Exception {
		JSONResponse<String> deleteResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
		Mockito.when(storeService.deleteEntity(Mockito.anyInt())).thenReturn(deleteResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/store/6");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":\"\"}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, response.getContentAsString());
	}
}
