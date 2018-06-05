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
import com.pricer.rest.dto.JSONResponse;
import com.pricer.rest.exception.ResourceNotFoundException;

public abstract class AbstractDataAccessService<E, S, K, 
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

	public JSONResponse<ResponseDTO> addEntity(RequestDTO entity) {
		S createdEntity = entityWithSequenceRepository.save(entity.toEntityWithSequence());
		ResponseDTO entityResponseDTO = dtoFactory.getResonseDTO();
		entityResponseDTO.buildResponseUsingSequence(createdEntity);
		JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
		return response;
	}

	public JSONResponse<ResponseDTO> updateEntity(RequestDTO entity, K key) {

		E entityToBeUpdated = entity.toEntity(key);
		E updatedEntity = entityRepository.save(entityToBeUpdated);
		ResponseDTO entityResponseDTO = dtoFactory.getResonseDTO();
		entityResponseDTO.buildResponse(updatedEntity);
		JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
		return response;
	}

	public JSONResponse<ResponseDTO> retriveEntity(K key) {
		Optional<E> entityOptional = entityRepository.findById(key);
		if (entityOptional.isPresent()) {
			ResponseDTO entityResponseDTO = dtoFactory.getResonseDTO();
			entityResponseDTO.buildResponse(entityOptional.get());
			JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public JSONResponse<List<ResponseDTO>> listEntities() {
		List<E> entities = entityRepository.findAll();
		List<ResponseDTO> entityResponseDTOs = new ArrayList<>(entities.size());
		entities.stream().forEach(entity -> {
			ResponseDTO newDTO = dtoFactory.getResonseDTO();
			newDTO.buildResponse(entity);
			entityResponseDTOs.add(newDTO);
		});
		JSONResponse<List<ResponseDTO>> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK,
				entityResponseDTOs);
		return response;
	}

	public JSONResponse<String> deleteProduct(K key) {
		entityRepository.deleteById(key);
		JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
		return response;
	}

}
