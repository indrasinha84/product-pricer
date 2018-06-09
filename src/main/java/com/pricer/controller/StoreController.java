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

import com.pricer.model.JSONResponse;
import com.pricer.model.Store;
import com.pricer.rest.exception.DependencyPresentException;
import com.pricer.rest.exception.IdNotAllowedException;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.rest.exception.ResourceModficationException;
import com.pricer.rest.exception.ResourceNotCreatedException;
import com.pricer.rest.exception.ResourceNotDeletedException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.impl.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	StoreService storeService;

	private static Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<Store> addStore(@Valid @RequestBody Store store) {
		JSONResponse<Store> result;
		try {
			result = storeService.addEntity(store);
		} catch (RuntimeException e) {
			LOGGER.error("Creation of store failed. ", e);
			throw new ResourceNotCreatedException("Store");
		}
		return result;

	}

	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<Store> putStore(@PathVariable Integer id, @Valid @RequestBody Store store) {
		JSONResponse<Store> result;
		try {
			result = storeService.putEntity(store, id);
		} catch (IdNotAllowedException e) {
			LOGGER.error("Id not allowed. ", e);
			throw e;
		}

		catch (RuntimeException e) {
			LOGGER.error("Store not modified. ", e);
			throw new ResourceModficationException("Store", "id", id);
		}
		return result;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<Store> retriveStore(@PathVariable Integer id) {
		JSONResponse<Store> result;
		try {
			result = storeService.findEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Store not found. ", e);
			throw new ResourceNotFoundException("Store", "id", id);
		} catch (RuntimeException e) {
			LOGGER.error("Store fetch failed for {}. ", id, e);
			throw new ResourceNotFoundException("Store", "id", id);
		}
		return result;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<List<Store>> listStores() {
		JSONResponse<List<Store>> result;
		try {
			result = storeService.listEntities();
		} catch (RuntimeException e) {
			LOGGER.error("Store listing failed. ", e);
			throw new ResourceListingException("Store");
		}
		return result;
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<String> deleteStore(@PathVariable Integer id) {
		JSONResponse<String> result;
		try {
			result = storeService.deleteEntity(id);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Store not found during deletion. ", e);
			throw new ResourceNotFoundException("Store", "id", id);
		} catch (DependencyPresentException e) {
			LOGGER.error("Store deletion failed due to dependency present for {}. ", id, e);
			throw new DependencyPresentException("Store", "id", id);
		} catch (RuntimeException e) {
			LOGGER.error("Store deletion failed for {}. ", id, e);
			throw new ResourceNotDeletedException("Store", "id", id);
		}
		return result;
	}
}
