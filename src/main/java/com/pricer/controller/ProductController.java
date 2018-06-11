package com.pricer.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;
import com.pricer.model.Product;
import com.pricer.repository.ProductRepository;
import com.pricer.rest.exception.IdNotAllowedException;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.rest.exception.ResourceModficationException;
import com.pricer.rest.exception.ResourceNotCreatedException;
import com.pricer.rest.exception.ResourceNotDeletedException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.DataAccessService;
import com.pricer.service.impl.PriceDetailsCacheService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	@Resource(name = "priceDetailsCacheService")
	PriceDetailsCacheService priceDetailsService;

	@Autowired
	DataAccessService<Product, Integer, ProductRepository> productService;

	private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<Product> addProduct(@Valid @RequestBody Product product) {
		JSONResponse<Product> result;
		try {
			product.setCreatedDate(new Date());
			result = productService.addEntity(product);
		} catch (RuntimeException e) {
			LOGGER.error("Creation of product failed. ", e);
			throw new ResourceNotCreatedException("Product");
		}
		return result;

	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<Product> putProduct(@PathVariable Integer id, @Valid @RequestBody Product product) {
		JSONResponse<Product> result;
		try {
			result = productService.putEntity(product, id);
		} catch (IdNotAllowedException e) {
			LOGGER.error("Id not allowed. ", e);
			throw e;
		} catch (RuntimeException e) {
			LOGGER.error("Product not modified. ", e);
			throw new ResourceModficationException("Product", "id", id);
		}
		return result;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<Product> retriveProduct(@PathVariable Integer id) {
		JSONResponse<Product> result;
		try {
			result = productService.findEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Product not found. ", e);
			throw new ResourceNotFoundException("Product", "id", id);
		} catch (RuntimeException e) {
			LOGGER.error("Product fetch failed for {}. ", id, e);
			throw new ResourceNotFoundException("Product", "id", id);
		}
		return result;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<List<Product>> listProducts() {
		JSONResponse<List<Product>> result;
		try {
			result = productService.listEntities();
		} catch (RuntimeException e) {
			LOGGER.error("Product listing failed. ", e);
			throw new ResourceListingException("Product");
		}
		return result;
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<String> deleteProduct(@PathVariable Integer id) {
		JSONResponse<String> result;
		try {
			result = productService.deleteEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Product not found during deletion. ", e);
			throw new ResourceNotFoundException("Product", "id", id);
		} catch (RuntimeException e) {
			LOGGER.error("Product deletion failed for {}. ", id, e);
			throw new ResourceNotDeletedException("Product", "id", id);
		}
		return result;
	}

	@GetMapping(path = "/{product}/prices", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<PriceDetails> getPriceDetailsForAProduct(@PathVariable Integer product) {
		JSONResponse<PriceDetails> result;
		try {
			result = priceDetailsService.getPriceDetailsResponse(product);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Price Details not found. ", e);
			throw new ResourceNotFoundException("Price Details", "product", product);
		} catch (RuntimeException e) {
			LOGGER.error("Price Details fetch failed for {}.", product, e);
			throw new ResourceNotFoundException("Price Details", "product", product);
		}
		return result;
	}

}
