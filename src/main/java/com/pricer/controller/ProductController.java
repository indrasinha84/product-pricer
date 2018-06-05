package com.pricer.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.pricer.rest.dto.JSONResponse;
import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;
import com.pricer.rest.exception.IdNotAllowedException;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.rest.exception.ResourceModficationException;
import com.pricer.rest.exception.ResourceNotCreatedException;
import com.pricer.rest.exception.ResourceNotDeletedException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO product) {
		JSONResponse<ProductResponseDTO> result;
		try {
			result = productService.addEntity(product);
		} catch (RuntimeException e) {
			LOGGER.error("Creation of product failed. ", e);
			throw new ResourceNotCreatedException("Product");
		}
		return result;

	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<ProductResponseDTO> updateProduct(@PathVariable Integer id,
			@Valid @RequestBody ProductRequestDTO product) {
		JSONResponse<ProductResponseDTO> result;
		try {

			result = productService.updateEntity(product, id);
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
	public JSONResponse<ProductResponseDTO> retriveProduct(@PathVariable Integer id) {
		JSONResponse<ProductResponseDTO> result;
		try {
			result = productService.retriveEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Product not found. ", e);
			throw new ResourceNotFoundException("Product", "id", id);
		}
		catch (RuntimeException e) {
			LOGGER.error("Product fetch failed for {}. ", id, e);
			throw new ResourceNotFoundException("Product", "id", id);
		}
		return result;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<List<ProductResponseDTO>> listProducts() {
		JSONResponse<List<ProductResponseDTO>> result;
		try {
		result = productService.listEntities();
		}
		catch (RuntimeException e) {
			LOGGER.error("Product listing failed. ", e);
			throw new ResourceListingException("Product");
		}
		return result;
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<String> deleteProduct(@PathVariable Integer id) {
		JSONResponse<String> result;
		try {
		result = productService.deleteEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Product not found during deletion. ", e);
			throw new ResourceNotFoundException("Product", "id", id);
		}
		catch (RuntimeException e) {
			LOGGER.error("Product deletion failed for {}. ", id, e);
			throw new ResourceNotDeletedException("Product", "id", id);
		}
		return result;
	}
}
