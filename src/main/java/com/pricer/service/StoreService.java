package com.pricer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pricer.repository.ProductWithSequenceRepository;

@Service
public class StoreService {

	@Autowired
	ProductWithSequenceRepository productRepository;

//	public RESTResponse<Product> addProduct(Product product) {
//		Product createdProduct = productRepository.save(product);		
//		RESTResponse<Product> response = new RESTResponse<>(HttpStatus.OK, 
//				RESTMessage.OK, createdProduct);
//		return response;
//	}

}
