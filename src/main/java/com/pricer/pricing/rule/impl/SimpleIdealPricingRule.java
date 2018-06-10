package com.pricer.pricing.rule.impl;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pricer.model.Product;
import com.pricer.pricing.rule.IdealPricingRule;

@Component("simpleIdealPricingRuleStrategy")
public class SimpleIdealPricingRule implements IdealPricingRule {

	@Override
	public Double getIdealPrice(Product product) {
		Double idealPrice = null;

		if (product != null && product.getMarketPrices() != null && !product.getMarketPrices().isEmpty()
				&& product.getMarketPrices().size() > 4) {
			Double average = product.getMarketPrices().stream().map(p -> p.getStorePrice()).sorted()
					.collect(Collectors.toList()).subList(2, product.getMarketPrices().size() - 3).stream()
					.collect(Collectors.averagingDouble(p -> p)).doubleValue();
			idealPrice = average * 1.2;

		}
		return idealPrice;
	}

}
