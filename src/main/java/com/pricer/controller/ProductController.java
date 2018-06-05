package com.pricer.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;
import com.pricer.rest.dto.RESTResponse;
import com.pricer.rest.exception.ResourceModficationException;
import com.pricer.rest.exception.ResourceNotCreatedException;
import com.pricer.rest.exception.ResourceNotDeletedException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RESTResponse<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO product) {
		RESTResponse<ProductResponseDTO> result;
		try {
			result = productService.addProduct(product);
		} catch (RuntimeException e) {
			LOGGER.error("Creation of product failed. ", e);
			throw new ResourceNotCreatedException("product");
		}
		return result;

	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RESTResponse<ProductResponseDTO> updateProduct(@PathVariable Integer id,
			@RequestBody ProductRequestDTO product) {
		RESTResponse<ProductResponseDTO> result;
		try {

			result = productService.updateProduct(product, id);
		} catch (RuntimeException e) {
			LOGGER.error("Product not modified. ", e);
			throw new ResourceModficationException("product", "id", id);
		}
		return result;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RESTResponse<ProductResponseDTO> retriveProduct(@PathVariable Integer id) {
		RESTResponse<ProductResponseDTO> result;
		try {
			result = productService.retriveProduct(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Product not found. ", e);
			throw e;
		}
		catch (RuntimeException e) {
			LOGGER.error("Product fetch failed for {}. ", id, e);
			throw new ResourceNotFoundException("Product", "id", id);
		}
		return result;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RESTResponse<List<ProductResponseDTO>> listProducts() {
		RESTResponse<List<ProductResponseDTO>> result;
		try {
		result = productService.listProducts();
		}
		catch (RuntimeException e) {
			LOGGER.error("Product listing failed. ", e);
			throw new ResourceListingException("Product");
		}
		return result;
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public RESTResponse<ProductResponseDTO> deleteProduct(@PathVariable Integer id) {
		RESTResponse<ProductResponseDTO> result;
		try {
		result = productService.deleteProduct(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Product not found during deletion. ", e);
			throw e;
		}
		catch (RuntimeException e) {
			LOGGER.error("Product deletion failed for {}. ", id, e);
			throw new ResourceNotDeletedException("Product", "id", id);
		}
		return result;
	}
}
