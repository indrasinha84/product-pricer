package com.pricer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pricer.entity.Product;
import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;
import com.pricer.rest.dto.RESTResponse;
import com.pricer.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public RESTResponse<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO product) {
		RESTResponse<ProductResponseDTO> result = productService.addProduct(product);
		return result;
	}

}
