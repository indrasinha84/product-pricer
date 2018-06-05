package com.pricer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pricer.entity.Product;
import com.pricer.entity.RESTMessage;
import com.pricer.entity.sequence.ProductWithSequence;
import com.pricer.repository.ProductRepository;
import com.pricer.repository.ProductWithSequenceRepository;
import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;
import com.pricer.rest.dto.RESTResponse;
import com.pricer.rest.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	ProductWithSequenceRepository productWithSequenceRepository;

	@Autowired
	ProductRepository productRepository;

	public RESTResponse<ProductResponseDTO> addProduct(ProductRequestDTO product) {
		ProductWithSequence createdProduct = productWithSequenceRepository.save(product.toEntityWithSequence());
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		productResponseDTO.buildResponseUsingSequence(createdProduct);
		RESTResponse<ProductResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
				productResponseDTO);
		return response;
	}

	public RESTResponse<ProductResponseDTO> updateProduct(ProductRequestDTO product, Integer id) {

		Product productToBeUpdated = product.toEntity(id);
		Product updatedProduct = productRepository.save(productToBeUpdated);
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		productResponseDTO.buildResponse(updatedProduct);
		RESTResponse<ProductResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
				productResponseDTO);
		return response;
	}

	public RESTResponse<ProductResponseDTO> retriveProduct(Integer id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if (productOptional.isPresent()) {
			ProductResponseDTO productResponseDTO = new ProductResponseDTO();
			productResponseDTO.buildResponse(productOptional.get());
			RESTResponse<ProductResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
					productResponseDTO);
			return response;
		} else {
			throw new ResourceNotFoundException("Product", "id", id);
		}
	}

	public RESTResponse<List<ProductResponseDTO>> listProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductResponseDTO> productResponseDTOs = new ArrayList<>(products.size());
		products.stream()
				.forEach(product -> 
						{
							ProductResponseDTO newDTO = new ProductResponseDTO();
							newDTO.buildResponse(product);
							productResponseDTOs.add(newDTO);
						});
		RESTResponse<List<ProductResponseDTO>> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
				productResponseDTOs);
		return response;
	}

	public RESTResponse<String> deleteProduct(Integer id) {
		productRepository.deleteById(id);
		RESTResponse<String> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
				"");
		return response;
	}

}
