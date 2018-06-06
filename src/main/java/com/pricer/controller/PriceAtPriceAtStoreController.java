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
import com.pricer.rest.dto.PriceAtStoreRequestDTO;
import com.pricer.rest.dto.PriceAtStoreResponseDTO;
import com.pricer.rest.exception.IdNotAllowedException;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.rest.exception.ResourceModficationException;
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
	public JSONResponse<PriceAtStoreResponseDTO> addPriceAtStore
	(@Valid @RequestBody PriceAtStoreRequestDTO priceAtStore) {
		JSONResponse<PriceAtStoreResponseDTO> result;
		try {
			result = priceAtStoreService.addEntity(priceAtStore);
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection failed. ", e);
			throw new ResourceNotCreatedException("Price Collection");
		}
		return result;

	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<PriceAtStoreResponseDTO> updatePriceAtStore(@PathVariable Integer id,
			@Valid @RequestBody PriceAtStoreRequestDTO priceAtStore) {
		JSONResponse<PriceAtStoreResponseDTO> result;
		try {

			result = priceAtStoreService.updateEntity(priceAtStore, id);
		} catch (IdNotAllowedException e) {
			LOGGER.error("Id not allowed. ", e);
			throw e;
		}
		
		catch (RuntimeException e) {
			LOGGER.error("PriceAtStore not modified. ", e);
			throw new ResourceModficationException("PriceAtStore", "id", id);
		}
		return result;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<PriceAtStoreResponseDTO> retrivePriceAtStore(@PathVariable Integer id) {
		JSONResponse<PriceAtStoreResponseDTO> result;
		try {
			result = priceAtStoreService.retriveEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("PriceAtStore not found. ", e);
			throw new ResourceNotFoundException("PriceAtStore", "id", id);
		}
		catch (RuntimeException e) {
			LOGGER.error("PriceAtStore fetch failed for {}. ", id, e);
			throw new ResourceNotFoundException("PriceAtStore", "id", id);
		}
		return result;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<List<PriceAtStoreResponseDTO>> listPriceAtStores() {
		JSONResponse<List<PriceAtStoreResponseDTO>> result;
		try {
		result = priceAtStoreService.listEntities();
		}
		catch (RuntimeException e) {
			LOGGER.error("PriceAtStore listing failed. ", e);
			throw new ResourceListingException("PriceAtStore");
		}
		return result;
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<String> deletePriceAtStore(@PathVariable Integer id) {
		JSONResponse<String> result;
		try {
		result = priceAtStoreService.deleteEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("PriceAtStore not found during deletion. ", e);
			throw new ResourceNotFoundException("PriceAtStore", "id", id);
		}
		catch (RuntimeException e) {
			LOGGER.error("PriceAtStore deletion failed for {}. ", id, e);
			throw new ResourceNotDeletedException("PriceAtStore", "id", id);
		}
		return result;
	}
}
