package com.pricer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.entity.Product;
import com.pricer.entity.RESTMessage;
import com.pricer.repository.ProductRepository;
import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;
import com.pricer.rest.dto.RESTResponse;
import com.pricer.rest.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public RESTResponse<ProductResponseDTO> addProduct(ProductRequestDTO product) {
		Product createdProduct = productRepository.save(product.toEntity());
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		productResponseDTO.buildResponse(createdProduct);
		RESTResponse<ProductResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
				productResponseDTO);
		return response;
	}

	public RESTResponse<ProductResponseDTO> updateProduct(ProductRequestDTO product, Integer id) {
		
		Product productToBeUpdated = product.toEntity();
		productToBeUpdated.setId(id);

		Product updatedProduct = productRepository.save(productToBeUpdated);
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		productResponseDTO.buildResponse(updatedProduct);
		RESTResponse<ProductResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
				productResponseDTO);
		return response;
	}

	public RESTResponse<ProductResponseDTO> retriveProduct(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public RESTResponse<List<ProductResponseDTO>> listProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	public RESTResponse<ProductResponseDTO> deleteProduct(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
