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
import com.pricer.model.PriceDetails;
import com.pricer.model.Product;
import com.pricer.model.RESTMessage;
import com.pricer.repository.ProductRepository;
import com.pricer.service.DataAccessService;
import com.pricer.service.impl.PriceDetailsCacheService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	DataAccessService<Product, Integer, ProductRepository> productService;
	
	@MockBean()
	PriceDetailsCacheService priceDetailsService;

	Product product = new Product("Test Product", "Test Product Descrption", 50000d);

	JSONResponse<Product> productJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, product);

	String testJson = "{\"name\": \"Test Product\",\"description\": \"Test Product Descrption\",\"basePrice\": \"50000\"}";

	@Test
	public final void testAddProduct() throws Exception {
		Mockito.when(productService.addEntity(Mockito.any(Product.class))).thenReturn(productJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product").accept(MediaType.APPLICATION_JSON)
				.content(testJson).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_UTF8, MediaType.valueOf(response.getHeader(HttpHeaders.CONTENT_TYPE)));
		String content = "{\"status\":200,\"message\":\"success\",\"payload\":{\"name\":\"Test Product\",\"description\":\"Test Product Descrption\",\"basePrice\":50000.0,\"created\":null,\"identifier\":null}}";
		assertEquals(content, response.getContentAsString());

	}

	@Test
	public final void testPutProduct() throws Exception {
		Mockito.when(productService.putEntity(Mockito.any(Product.class), Mockito.anyInt()))
				.thenReturn(productJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/6")
				.accept(MediaType.APPLICATION_JSON).content(testJson).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_UTF8, MediaType.valueOf(response.getHeader(HttpHeaders.CONTENT_TYPE)));
		String content = "{\"status\":200,\"message\":\"success\",\"payload\":{\"name\":\"Test Product\",\"description\":\"Test Product Descrption\",\"basePrice\":50000.0,\"created\":null,\"identifier\":null}}";
		assertEquals(content, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testRetriveProduct() throws Exception {
		Mockito.when(productService.findEntity(Mockito.anyInt())).thenReturn(productJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/6");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":{\"name\":\"Test Product\",\"description\":\"Test Product Descrption\",\"basePrice\":50000.0,\"created\":null,\"identifier\":null}}";
		assertEquals(expected, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public final void testListProducts() throws Exception {
		List<Product> list = new ArrayList<>(2);
		list.add(product);
		list.add(product);
		JSONResponse<List<Product>> productListJsonResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, list);
		Mockito.when(productService.listEntities()).thenReturn(productListJsonResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":[{\"name\":\"Test Product\",\"description\":\"Test Product Descrption\",\"basePrice\":50000.0,\"created\":null,\"identifier\":null},{\"name\":\"Test Product\",\"description\":\"Test Product Descrption\",\"basePrice\":50000.0,\"created\":null,\"identifier\":null}]}";
		assertEquals(expected, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public final void testDeleteProduct() throws Exception {
		JSONResponse<String> deleteResponse = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
		Mockito.when(productService.deleteEntity(Mockito.anyInt())).thenReturn(deleteResponse);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/6");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":\"\"}";
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, response.getContentAsString());
	}

	@Test
	public final void testGetPriceDetailsForAProduct() throws Exception {
		PriceDetails priceDetails = new PriceDetails(6, 4500d, 3000d, 6000d, 5000d, 5);
		JSONResponse<PriceDetails> priceDetailsJson = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, priceDetails);
		Mockito.when(priceDetailsService.getPriceDetailsResponse(Mockito.anyInt())).thenReturn(priceDetailsJson);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/6/prices");
		final MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String expected = "{\"status\":200,\"message\":\"success\",\"payload\":{\"product\":6,\"name\":null,\"description\":null,\"basePrice\":null,\"averagePrice\":4500.0,\"lowestPrice\":3000.0,\"highestPrice\":6000.0,\"idealPrice\":5000.0,\"count\":5}}";
		assertEquals(expected, response.getContentAsString());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	
	
	
	}

}
