package com.pricer.pricing.rule.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pricer.model.EffectiveStatus;
import com.pricer.model.MarketPrice;
import com.pricer.model.Product;
import com.pricer.pricing.rule.IdealPricingRule;

@Component("simpleIdealPricingRuleStrategy")
public class SimpleIdealPricingRule implements IdealPricingRule {

	@Override
	public Double getIdealPrice(Product product) {
		Double idealPrice = null;
		Set<MarketPrice> activeMarketPrices = null;
		if (product != null) {
			activeMarketPrices = getActiveMarketPrices(product.getMarketPrices());
		}
		if (activeMarketPrices != null && !activeMarketPrices.isEmpty() && activeMarketPrices.size() > 4) {
			Double average = activeMarketPrices.stream().map(p -> p.getStorePrice()).sorted()
					.collect(Collectors.toList()).subList(2, activeMarketPrices.size() - 2).stream()
					.collect(Collectors.averagingDouble(p -> p)).doubleValue();
			idealPrice = average * 1.2;

		}
		return idealPrice;
	}

	private Set<MarketPrice> getActiveMarketPrices(Set<MarketPrice> marketPrices) {
		return marketPrices.stream().filter(p -> EffectiveStatus.ACTIVE.equals(p.getEffectiveStatus()))
				.collect(Collectors.toSet());
	}

}
