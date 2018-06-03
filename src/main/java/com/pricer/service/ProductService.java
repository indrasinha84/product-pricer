package com.pricer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pricer.model.Product;
import com.pricer.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	public void getProducts() {
 		List<Product> prodLost = productRepository.findAll();
		System.out.println(prodLost.size());
	}
	
}
