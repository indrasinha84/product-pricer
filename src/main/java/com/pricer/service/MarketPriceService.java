package com.pricer.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.pricer.model.EffectiveStatus;
import com.pricer.model.JSONResponse;
import com.pricer.model.MarketPrice;
import com.pricer.repository.MarketPriceRepository;

@Service
public class MarketPriceService extends AbstractSoftDataAccessService<MarketPrice, Integer, MarketPriceRepository> {

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
		MarketPrice request = getEntityInstance();
		request.setStoreId(store);
		request.setProductId(product);
		return findEntityByExample(request);
	}

	public JSONResponse<String> deleteMarketPrice(Integer store, Integer product) {
		MarketPrice request = getEntityInstance();
		request.setStoreId(store);
		request.setProductId(product);
		return deleteEntityByExample(request);
	}

	public JSONResponse<MarketPrice> putEntity(@Valid MarketPrice marketPrice, Integer store, Integer product) {
		MarketPrice filters = getEntityInstance();
		filters.setStoreId(store);
		filters.setProductId(product);
		marketPrice.setStoreId(store);
		marketPrice.setProductId(product);
		return putEntityByExample(marketPrice, filters);
	}
}
