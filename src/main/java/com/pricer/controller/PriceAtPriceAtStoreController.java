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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.rest.dto.JSONResponse;
import com.pricer.rest.dto.PriceAtStoreRequestDTO;
import com.pricer.rest.dto.PriceAtStoreResponseDTO;
import com.pricer.rest.exception.ResourceAlreadyExist;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.rest.exception.ResourceNotCreatedException;
import com.pricer.rest.exception.ResourceNotDeletedException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.PriceAtStoreService;

@RestController
@RequestMapping("/store/product")
public class PriceAtPriceAtStoreController {

	@Autowired
	PriceAtStoreService priceAtStoreService;

	private static Logger LOGGER = LoggerFactory.getLogger(PriceAtPriceAtStoreController.class);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<PriceAtStoreResponseDTO> addPriceAtStore(
			@Valid @RequestBody PriceAtStoreRequestDTO priceAtStore) {
		JSONResponse<PriceAtStoreResponseDTO> result;
		try {
			result = priceAtStoreService.softAddEntity(priceAtStore);
		} catch (ResourceAlreadyExist e) {
			LOGGER.error("Price Collection failed. ", e);
			throw new ResourceAlreadyExist("Price Collection", "store/product",
					priceAtStore.getStore() + "/" + priceAtStore.getProduct());
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection failed. ", e);
			throw new ResourceNotCreatedException( "Price Collection");
		}
		return result;

	}
	
	@GetMapping(path = "/{store}/{product}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<PriceAtStoreResponseDTO> retrivePriceAtStore(@PathVariable Integer store, @PathVariable Integer product) {
		JSONResponse<PriceAtStoreResponseDTO> result;
		try {
			
			PriceAtStoreRequestDTO request = new PriceAtStoreRequestDTO();
			request.setStore(store);
			request.setProduct(product);
			result = priceAtStoreService.findByNaturalKey(request);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Price Collection not found. ", e);
			throw new ResourceNotFoundException("PriceAtStore", "store/product", store + "/" + product);
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection fetch failed for {}. ", store + "/" + product, e);
			throw new ResourceNotFoundException("PriceAtStore", "id", store + "/" + product);
		}
		return result;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<List<PriceAtStoreResponseDTO>> listPriceAtStores() {
		JSONResponse<List<PriceAtStoreResponseDTO>> result;
		try {
			result = priceAtStoreService.listActiveEntities();
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection listing failed. ", e);
			throw new ResourceListingException("Price Collection");
		}
		return result;
	}

	@DeleteMapping(path = "/{store}/{product}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<String> deletePriceAtStore(@PathVariable Integer store, @PathVariable Integer product) {
		JSONResponse<String> result;
		try {
			PriceAtStoreRequestDTO request = new PriceAtStoreRequestDTO();
			request.setStore(store);
			request.setProduct(product);
			result = priceAtStoreService.deleteEntityByNaturalKey(request);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("PriceAtStore not found during deletion. ", e);
			throw new ResourceNotFoundException("PriceAtStore", "id",  store + "/" + product);
		} catch (RuntimeException e) {
			LOGGER.error("PriceAtStore deletion failed for {}. ",  store + "/" + product, e);
			throw new ResourceNotDeletedException("PriceAtStore", "id",  store + "/" + product);
		}
		return result;
	}
}
