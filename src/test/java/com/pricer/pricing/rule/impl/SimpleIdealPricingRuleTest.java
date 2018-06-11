package com.pricer.pricing.rule.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.pricer.model.EffectiveStatus;
import com.pricer.model.MarketPrice;
import com.pricer.model.Product;
import com.pricer.pricing.rule.IdealPricingRule;
import com.pricer.service.impl.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleIdealPricingRuleTest {

	@Autowired
	@Qualifier("simpleIdealPricingRuleStrategy")
	private IdealPricingRule	simpleIdealPricingRuleStrategy;
	@MockBean
	ProductService							productService;
	Product									mockProduct		= new Product(1, "Test Product", "Test Description", 5000d);
	private final Set<MarketPrice>		marketPrice	= new HashSet<MarketPrice>();

	
	
	
	@Before
	public void setup() {
		marketPrice.add(new MarketPrice(1, 1, 1, "Product 1", 4500d, EffectiveStatus.ACTIVE));
		marketPrice.add(new MarketPrice(2, 2, 1, "Product 2", 6700d, EffectiveStatus.ACTIVE));
		marketPrice.add(new MarketPrice(3, 3, 1, "Product 3", 6000d, EffectiveStatus.ACTIVE));
		marketPrice.add(new MarketPrice(4, 4, 1, "Product 4", 3000d, EffectiveStatus.ACTIVE));
		marketPrice.add(new MarketPrice(5, 5, 1, "Product 5", 2000d, EffectiveStatus.ACTIVE));
		marketPrice.add(new MarketPrice(6, 6, 1, "Product 6", 10000d, EffectiveStatus.ACTIVE));
		mockProduct.setMarketPrices(marketPrice);
	}
	
	
	@Test
	public final void testGetIdealPrice() {
		Double idealPrice = simpleIdealPricingRuleStrategy.getIdealPrice(mockProduct);
		assertNotNull(idealPrice);
		assertTrue(idealPrice == 6300d);	}

}
