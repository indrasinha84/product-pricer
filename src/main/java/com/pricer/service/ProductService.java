package com.pricer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.model.Product;
import com.pricer.model.RESTMessage;
import com.pricer.model.RESTResponse;
import com.pricer.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	// private static Logger LOGGER =
	// LoggerFactory.getLogger(ProductService.class);

	public RESTResponse<Product> addProduct(Product product) {
		Product createdProduct = productRepository.save(product);
		RESTResponse<Product> response = new RESTResponse<>(HttpStatus.OK, 
				RESTMessage.OK, createdProduct);
		return response;
	}

}
