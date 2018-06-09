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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.model.JSONResponse;
import com.pricer.model.MarketPrice;
import com.pricer.rest.exception.IdNotAllowedException;
import com.pricer.rest.exception.ResourceAlreadyExists;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.rest.exception.ResourceModficationException;
import com.pricer.rest.exception.ResourceNotCreatedException;
import com.pricer.rest.exception.ResourceNotDeletedException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.impl.MarketPriceService;

@RestController
@RequestMapping("/store/product")
public class MarketPriceController {

	@Autowired
	MarketPriceService marketPriceService;

	private static Logger LOGGER = LoggerFactory.getLogger(MarketPriceController.class);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<MarketPrice> addMarketPrice(@Valid @RequestBody MarketPrice marketPrice) {
		JSONResponse<MarketPrice> result;
		try {
			result = marketPriceService.addEntity(marketPrice);
		} catch (ResourceAlreadyExists e) {
			LOGGER.error("Price Collection failed. ", e);
			throw new ResourceAlreadyExists("Price Collection", "store/product",
					marketPrice.getStoreId() + "/" + marketPrice.getProductId());
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection failed. ", e);
			throw new ResourceNotCreatedException("Price Collection");
		}
		return result;

	}
	
	@PutMapping(path = "/{store}/{product}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<MarketPrice> putMarketPrice(@PathVariable Integer store, @PathVariable Integer product, 
								@Valid @RequestBody MarketPrice marketPrice) {
		JSONResponse<MarketPrice> result;
		try {
			result = marketPriceService.putEntity(marketPrice, store, product);
		} catch (IdNotAllowedException e) {
			LOGGER.error("Id not allowed. ", e);
			throw e;
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection not modified. ", e);
			throw new ResourceModficationException("MarketPrice", "store/product",
					marketPrice.getStoreId() + "/" + marketPrice.getProductId());
		}
		return result;
	}	

	@GetMapping(path = "/{store}/{product}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<MarketPrice> retrieveMarketPrice(@PathVariable Integer store, @PathVariable Integer product) {
		JSONResponse<MarketPrice> result;
		try {
			result = marketPriceService.getMarketPrice(store, product);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Price Collection not found. ", e);
			throw new ResourceNotFoundException("MarketPrice", "store/product", store + "/" + product);
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection fetch failed for {}. ", store + "/" + product, e);
			throw new ResourceNotFoundException("MarketPrice", "id", store + "/" + product);
		}
		return result;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<List<MarketPrice>> listMarketPrices() {
		JSONResponse<List<MarketPrice>> result;
		try {
			result = marketPriceService.listEntities();
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection listing failed. ", e);
			throw new ResourceListingException("Price Collection");
		}
		return result;
	}

	@DeleteMapping(path = "/{store}/{product}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONResponse<String> deleteMarketPrice(@PathVariable Integer store, @PathVariable Integer product) {
		JSONResponse<String> result;
		try {
			result = marketPriceService.deleteMarketPrice(store, product);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("Price Collection not found during deletion. ", e);
			throw new ResourceNotFoundException("MarketPrice", "id", store + "/" + product);
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection deletion failed for {}. ", store + "/" + product, e);
			throw new ResourceNotDeletedException("MarketPrice", "id", store + "/" + product);
		}
		return result;
	}
}
