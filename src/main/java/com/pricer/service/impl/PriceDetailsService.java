package com.pricer.service.impl;

import org.springframework.stereotype.Service;

import com.pricer.model.EffectiveStatus;
import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;
import com.pricer.repository.PriceDetailsRepository;

@Service("priceDetailsServiceSoft")
public class PriceDetailsService extends AbstractSoftDataAccessService<PriceDetails, Integer, PriceDetailsRepository> {

	@Override
	protected void setEffectiveStatus(PriceDetails entity, EffectiveStatus effectiveStatus) {
		entity.setEffectiveStatus(effectiveStatus);
	}

	@Override
	protected PriceDetails getEntityInstance() {
		return new PriceDetails();
	}

	public JSONResponse<PriceDetails> getPriceDetails(Integer product) {
		PriceDetails request = getEntityInstance();
		request.setProductId(product);
		return findEntityByExample(request);
	}

	@Override
	protected PriceDetails setNaturalKey(PriceDetails request) {
		PriceDetails lookup = getEntityInstance();
		lookup.setProductId(request.getProductId());
		return lookup;
	}

	public void addOrReplacePriceDetails(PriceDetails priceDetails) {
		PriceDetails filters = setNaturalKey(priceDetails);
		putEntityByExample(priceDetails, filters);

	}
	
	
}
