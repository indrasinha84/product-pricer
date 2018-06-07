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
import com.pricer.rest.dto.MarketPriceRequestDTO;
import com.pricer.rest.dto.MarketPriceResponseDTO;
import com.pricer.rest.exception.ResourceAlreadyExist;
import com.pricer.rest.exception.ResourceListingException;
import com.pricer.rest.exception.ResourceNotCreatedException;
import com.pricer.rest.exception.ResourceNotDeletedException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.MarketPriceService;

@RestController
@RequestMapping("/store/product")
public class MarketPriceController {

	@Autowired
	MarketPriceService marketPriceService;

	private static Logger LOGGER = LoggerFactory.getLogger(MarketPriceController.class);

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<MarketPriceResponseDTO> addMarketPrice(
			@Valid @RequestBody MarketPriceRequestDTO marketPrice) {
		JSONResponse<MarketPriceResponseDTO> result;
		try {
			result = marketPriceService.softAddEntity(marketPrice);
		} catch (ResourceAlreadyExist e) {
			LOGGER.error("Price Collection failed. ", e);
			throw new ResourceAlreadyExist("Price Collection", "store/product",
					marketPrice.getStore() + "/" + marketPrice.getProduct());
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection failed. ", e);
			throw new ResourceNotCreatedException( "Price Collection");
		}
		return result;

	}
	
	@GetMapping(path = "/{store}/{product}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<MarketPriceResponseDTO> retriveMarketPrice(@PathVariable Integer store, @PathVariable Integer product) {
		JSONResponse<MarketPriceResponseDTO> result;
		try {
			
			MarketPriceRequestDTO request = new MarketPriceRequestDTO();
			request.setStore(store);
			request.setProduct(product);
			result = marketPriceService.findByNaturalKey(request);
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
	public JSONResponse<List<MarketPriceResponseDTO>> listMarketPrices() {
		JSONResponse<List<MarketPriceResponseDTO>> result;
		try {
			result = marketPriceService.listActiveEntities();
		} catch (RuntimeException e) {
			LOGGER.error("Price Collection listing failed. ", e);
			throw new ResourceListingException("Price Collection");
		}
		return result;
	}

	@DeleteMapping(path = "/{store}/{product}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONResponse<String> deleteMarketPrice(@PathVariable Integer store, @PathVariable Integer product) {
		JSONResponse<String> result;
		try {
			MarketPriceRequestDTO request = new MarketPriceRequestDTO();
			request.setStore(store);
			request.setProduct(product);
			result = marketPriceService.deleteEntityByNaturalKey(request);
		} catch (ResourceNotFoundException e) {
			LOGGER.error("MarketPrice not found during deletion. ", e);
			throw new ResourceNotFoundException("MarketPrice", "id",  store + "/" + product);
		} catch (RuntimeException e) {
			LOGGER.error("MarketPrice deletion failed for {}. ",  store + "/" + product, e);
			throw new ResourceNotDeletedException("MarketPrice", "id",  store + "/" + product);
		}
		return result;
	}
}
