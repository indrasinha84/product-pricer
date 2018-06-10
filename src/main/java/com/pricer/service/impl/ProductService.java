package com.pricer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pricer.model.Product;
import com.pricer.repository.ProductRepository;

@Service
public class ProductService
		extends AbstractCRUDDataAccessService<Product, Integer, ProductRepository> {

	@Override
	protected void setKey(Product request, Integer key) {
		request.setId(key);		
	}

	public List<Product> getProductsForPriceCalculation(Integer chunkStartPosition,
			Integer chunkEndPosition) {
		return repository.getProductsForPriceCalculation(chunkStartPosition,
				 chunkEndPosition) ;
	}


}
