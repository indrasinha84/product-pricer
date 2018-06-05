package com.pricer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.entity.Product;
import com.pricer.entity.RESTMessage;
import com.pricer.repository.ProductRepository;
import com.pricer.rest.dto.RESTResponse;

@Service
public class StoreService {

	@Autowired
	ProductRepository productRepository;

//	public RESTResponse<Product> addProduct(Product product) {
//		Product createdProduct = productRepository.save(product);		
//		RESTResponse<Product> response = new RESTResponse<>(HttpStatus.OK, 
//				RESTMessage.OK, createdProduct);
//		return response;
//	}

}
