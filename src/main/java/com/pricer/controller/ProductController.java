package com.pricer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.model.Product;
import com.pricer.model.RESTResponse;
import com.pricer.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService ;

	@PostMapping("")
	public RESTResponse<Product> addProduct(@Valid @RequestBody Product product) {
		RESTResponse<Product> result = productService.addProduct(product);
		return result;
	}

}
