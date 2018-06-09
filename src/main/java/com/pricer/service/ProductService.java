package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.model.Product;
import com.pricer.repository.ProductRepository;

@Service
public class ProductService
		extends CRUDDataAccessService<Product, Integer, ProductRepository> {

	@Override
	protected void setKey(Product request, Integer key) {
		request.setId(key);		
	}


}
