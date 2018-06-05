package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.entity.Product;
import com.pricer.entity.sequence.ProductWithSequence;
import com.pricer.repository.ProductRepository;
import com.pricer.repository.ProductWithSequenceRepository;
import com.pricer.rest.dto.ProductRequestDTO;
import com.pricer.rest.dto.ProductResponseDTO;
import com.pricer.rest.dto.factory.ProductResponseDTOFactory;

@Service
public class ProductService
		extends AbstractDataAccessService<Product, ProductWithSequence, Integer, 
		ProductResponseDTO, ProductRequestDTO, ProductRepository, ProductWithSequenceRepository, ProductResponseDTOFactory> {

}
