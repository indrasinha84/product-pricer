package com.pricer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.service.ProductService;

@RestController
@RequestMapping("/store")
public class StoreController {

	
	@Autowired
	ProductService productService;
//
//	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, 
//			consumes = MediaType.APPLICATION_JSON_VALUE)
//	public RESTResponse<Product> addProduct(@Valid @RequestBody Product product) {
////		RESTResponse<Product> result = productService.addProduct(product);
////		return result;
//	}
}
