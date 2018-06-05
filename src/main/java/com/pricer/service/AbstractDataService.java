package com.pricer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.pricer.entity.RESTMessage;
import com.pricer.rest.dto.DTOFactory;
import com.pricer.rest.dto.IJSONRequest;
import com.pricer.rest.dto.IJSONResponse;
import com.pricer.rest.dto.RESTResponse;
import com.pricer.rest.exception.ResourceNotFoundException;

public abstract class AbstractDataService<E, S, K, 
								ResponseDTO extends IJSONResponse<E, S>,
								RequestDTO extends IJSONRequest<E, S, K>,
								ERepository extends JpaRepository<E, K>,
								SRepository extends JpaRepository<S, K>,
								DTOF extends DTOFactory<ResponseDTO>
								> {
	@Autowired
	SRepository entityWithSequenceRepository;
	
	@Autowired
	ERepository entityRepository;
	
	@Autowired
	DTOF dtoFactory;

	public RESTResponse<ResponseDTO> addProduct(RequestDTO product) {
		S createdProduct = entityWithSequenceRepository.save(product.toEntityWithSequence());
		ResponseDTO productResponseDTO = dtoFactory.getResonseDTO();
		productResponseDTO.buildResponseUsingSequence(createdProduct);
		RESTResponse<ResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK, productResponseDTO);
		return response;
	}

	public RESTResponse<ResponseDTO> updateProduct(RequestDTO product, K id) {

		E productToBeUpdated = product.toEntity(id);
		E updatedProduct = entityRepository.save(productToBeUpdated);
		ResponseDTO productResponseDTO = dtoFactory.getResonseDTO();
		productResponseDTO.buildResponse(updatedProduct);
		RESTResponse<ResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK, productResponseDTO);
		return response;
	}

	public RESTResponse<ResponseDTO> retriveProduct(K id) {
		Optional<E> productOptional = entityRepository.findById(id);
		if (productOptional.isPresent()) {
			ResponseDTO productResponseDTO = dtoFactory.getResonseDTO();
			productResponseDTO.buildResponse(productOptional.get());
			RESTResponse<ResponseDTO> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK, productResponseDTO);
			return response;
		} else {
			throw new ResourceNotFoundException("E", "id", id);
		}
	}

	public RESTResponse<List<ResponseDTO>> listProducts() {
		List<E> products = entityRepository.findAll();
		List<ResponseDTO> productResponseDTOs = new ArrayList<>(products.size());
		products.stream().forEach(product -> {
			ResponseDTO newDTO = dtoFactory.getResonseDTO();
			newDTO.buildResponse(product);
			productResponseDTOs.add(newDTO);
		});
		RESTResponse<List<ResponseDTO>> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK,
				productResponseDTOs);
		return response;
	}

	public RESTResponse<String> deleteProduct(K id) {
		entityRepository.deleteById(id);
		RESTResponse<String> response = new RESTResponse<>(HttpStatus.OK, RESTMessage.OK, "");
		return response;
	}

}
