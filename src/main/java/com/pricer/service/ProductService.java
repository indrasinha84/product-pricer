package com.pricer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.entity.Product;
import com.pricer.entity.RESTMessage;
import com.pricer.repository.ProductRepository;
import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;
import com.pricer.rest.dto.RESTResponse;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public RESTResponse<ProductResponseDTO> addProduct(ProductRequestDTO product) {
		Product createdProduct = productRepository.save(product.toEntity());
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		productResponseDTO.buildResponse(createdProduct);
		RESTResponse<ProductResponseDTO> response = new RESTResponse<>(HttpStatus.OK, 
				RESTMessage.OK, productResponseDTO);
		return response;
	}

}
