package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.entity.Product;
import com.pricer.repository.ProductRepository;
import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;

@Service
public class ProductService
		extends AbstractDataAccessService<Product, Integer, ProductResponseDTO, ProductRequestDTO, ProductRepository> {

	@Override
	public ProductResponseDTO getResonseDTO() {
		return new ProductResponseDTO();
	}

}
