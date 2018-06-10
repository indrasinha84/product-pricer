package com.pricer.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.model.EffectiveStatus;
import com.pricer.model.JSONResponse;
import com.pricer.model.PriceDetails;
import com.pricer.model.RESTMessage;
import com.pricer.repository.PriceDetailsRepository;

@Service("priceDetailsServiceSoft")
public class PriceDetailsService extends AbstractSoftDataAccessService<PriceDetails, Integer, PriceDetailsRepository> {

	private static Logger LOGGER = LoggerFactory.getLogger(PriceDetailsService.class);

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

	public List<JSONResponse<PriceDetails>> addOrReplacePriceDetails(List<PriceDetails> priceDetailsList) {
		try {
			List<PriceDetails> toInActive = new LinkedList<>();
			List<PriceDetails> toActive = new LinkedList<>();

			priceDetailsList.stream().forEach(p -> {
				PriceDetails filter = setNaturalKey(p);
				setEffectiveStatus(filter, EffectiveStatus.ACTIVE);
				Example<PriceDetails> example = Example.of(filter);
				Optional<PriceDetails> entityOptional = repository.findOne(example);
				if (entityOptional.isPresent()) {
					PriceDetails old = entityOptional.get();
					setEffectiveStatus(old, EffectiveStatus.INACTIVE);
					toInActive.add(old);
				}
				setEffectiveStatus(p, EffectiveStatus.ACTIVE);
				toActive.add(p);
			});
			toInActive.addAll(toActive);
			repository.saveAll(toInActive);
			List<JSONResponse<PriceDetails>> response = toActive.stream()
					.map(p -> new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, p)).collect(Collectors.toList());
			return response;
		} catch (Exception e) {
			LOGGER.error("addOrReplacePriceDetails failed.", e);
			throw e;
		}
	}

	@Override
	protected PriceDetails copyEntityForDelete(PriceDetails old) {
		PriceDetails e = getEntityInstance();
		e.setProductId(old.getProductId());
		e.setCountOfPrices(0);
		e.setAverageStorePrice(old.getAverageStorePrice());
		e.setHighestPrice(old.getHighestPrice());
		e.setIdealPrice(old.getIdealPrice());
		e.setLowestPrice(old.getIdealPrice());
		return e;
	}

}
