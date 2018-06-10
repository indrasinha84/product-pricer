package com.pricer.pricing.rule.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pricer.model.PriceDetails;
import com.pricer.model.Product;
import com.pricer.pricing.rule.IdealPricingRule;

@Component
public class PricingCalculator {
//TODO Create Strategy for PricingCalculator
	private static Logger LOGGER = LoggerFactory.getLogger(PricingCalculator.class);

	public IdealPricingRule getIdealPricingRule() {
		return idealPricingRule;
	}

	public void setIdealPricingRule(IdealPricingRule idealPricingRule) {
		this.idealPricingRule = idealPricingRule;
	}

	@Autowired
	@Qualifier("simpleIdealPricingRuleStrategy")
	IdealPricingRule idealPricingRule;

	public PriceDetails getDetailsForAProduct(Product product) {
		try {

			// DoubleSummaryStatistics stats =
			// product.getMarketPrices().stream()
			// .collect(Collectors.summarizingDouble(MarketPrice::getStorePrice));
			Integer countOfPrices = product.getMarketPrices().size();
			PriceDetails result = new PriceDetails(product, null, Double.MAX_VALUE, Double.MIN_VALUE, 0d, 0);

			product.getMarketPrices().stream().forEach(p -> {
				// Highest Price
				if (p.getStorePrice().compareTo(result.getHighestPrice()) >= 0) {
					result.setHighestPrice(p.getStorePrice());
				}
				// Lowest Price
				if (p.getStorePrice().compareTo(result.getLowestPrice()) <= 0) {
					result.setLowestPrice(p.getStorePrice());
				}
				// Total Price
				result.setAverageStorePrice(
						(result.getAverageStorePrice() == null ? 0d : result.getAverageStorePrice()) + p.getStorePrice());
			});

			result.setAverageStorePrice(result.getAverageStorePrice() / countOfPrices);
			result.setLowestPrice(result.getLowestPrice() == Double.MAX_VALUE ? null : result.getLowestPrice());
			result.setHighestPrice(result.getHighestPrice() == Double.MIN_VALUE ? null : result.getHighestPrice());

			// Ideal Prices
			Double idealPrice = idealPricingRule.getIdealPrice(product);
			result.setIdealPrice(idealPrice);
			return result;
		} catch (Exception e) {
			LOGGER.error("Price Calculation Failed.", e);
			throw e;
		}
	}
}
