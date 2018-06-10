package com.pricer.service.impl;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pricer.model.EffectiveStatus;
import com.pricer.model.JSONResponse;
import com.pricer.model.MarketPrice;
import com.pricer.repository.MarketPriceRepository;

@Service
public class MarketPriceService extends AbstractSoftDataAccessService<MarketPrice, Integer, MarketPriceRepository> {

	private static Logger LOGGER = LoggerFactory.getLogger(MarketPriceService.class);

	@Override
	protected void setEffectiveStatus(MarketPrice entity, EffectiveStatus effectiveStatus) {
		entity.setEffectiveStatus(effectiveStatus);
	}

	@Override
	protected MarketPrice getEntityInstance() {
		return new MarketPrice();
	}

	@Override
	protected MarketPrice setNaturalKey(MarketPrice request) {
		MarketPrice lookup = getEntityInstance();
		lookup.setProductId(request.getProductId());
		lookup.setStoreId(request.getStoreId());
		return lookup;
	}

	public JSONResponse<MarketPrice> getMarketPrice(Integer store, Integer product) {
		try {
			MarketPrice request = getEntityInstance();
			request.setStoreId(store);
			request.setProductId(product);
			return findEntityByExample(request);
		} catch (Exception e) {
			LOGGER.error("deleteMarketPrice failed.", e);
			throw e;
		}
	}

	public JSONResponse<String> deleteMarketPrice(Integer store, Integer product) {
		try {
			MarketPrice request = getEntityInstance();
			request.setStoreId(store);
			request.setProductId(product);
			return deleteEntityByExample(request);
		} catch (Exception e) {
			LOGGER.error("deleteMarketPrice failed.", e);
			throw e;
		}
	}

	public JSONResponse<MarketPrice> putEntity(@Valid MarketPrice marketPrice, Integer store, Integer product) {
		try {
			MarketPrice filters = getEntityInstance();
			filters.setStoreId(store);
			filters.setProductId(product);
			marketPrice.setStoreId(store);
			marketPrice.setProductId(product);
			return putEntityByExample(marketPrice, filters);
		} catch (Exception e) {
			LOGGER.error("putEntity failed.", e);
			throw e;
		}
	}

	@Override
	protected MarketPrice copyEntityForDelete(MarketPrice old) {
		try {
			MarketPrice e = getEntityInstance();
			e.setProductId(old.getProductId());
			e.setStoreId(old.getStoreId());
			e.setNotes(old.getNotes());
			e.setStorePrice(old.getStorePrice());
			return e;
		} catch (Exception e) {
			LOGGER.error("copyEntityForDelete failed.", e);
			throw e;
		}
	}

}
