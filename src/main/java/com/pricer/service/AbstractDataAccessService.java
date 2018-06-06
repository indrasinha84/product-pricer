package com.pricer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.pricer.entity.RESTMessage;
import com.pricer.rest.dto.IJSONRequest;
import com.pricer.rest.dto.IJSONResponse;
import com.pricer.rest.dto.JSONResponse;
import com.pricer.rest.exception.ResourceNotFoundException;

public abstract class AbstractDataAccessService<E, K, ResponseDTO extends IJSONResponse<E>, RequestDTO extends IJSONRequest<E, K>, R extends JpaRepository<E, K>> {

	@Autowired
	R repository;

	private final String ACTIVE = "A";
	private final String INACTIVE = "I";

	public abstract ResponseDTO getResonseDTO();

	public JSONResponse<ResponseDTO> addEntity(RequestDTO requestDTO) {
		E createdEntity = repository.save(requestDTO.toEntity(null));
		ResponseDTO entityResponseDTO = getResonseDTO();
		entityResponseDTO.buildResponse(createdEntity);
		JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
		return response;
	}

	public JSONResponse<ResponseDTO> softAddEntity(RequestDTO requestDTO) {
		Example<E> example = requestDTO.buildExampleUsingNaturalKey();
 		updateEffectiveStatus(example.getProbe(), ACTIVE);

		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			E old = entityOptional.get();
			updateEffectiveStatus(old, INACTIVE);
			repository.save(old);
		}
		E newEntity = requestDTO.toEntity(null);
		updateEffectiveStatus(newEntity, ACTIVE);
		E createdEntity = repository.save(newEntity);
		ResponseDTO entityResponseDTO = getResonseDTO();
		entityResponseDTO.buildResponse(createdEntity);
		JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
		return response;

	}

	public JSONResponse<ResponseDTO> updateEntity(RequestDTO requestDTO, K key) {
		E entityToBeUpdated = requestDTO.toEntity(key);
		E updatedEntity = repository.save(entityToBeUpdated);
		ResponseDTO entityResponseDTO = getResonseDTO();
		entityResponseDTO.buildResponse(updatedEntity);
		JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
		return response;
	}

	public abstract void updateEffectiveStatus(E old, String string);

	public JSONResponse<ResponseDTO> retriveEntity(K key) {
		Optional<E> entityOptional = repository.findById(key);
		if (entityOptional.isPresent()) {
			ResponseDTO entityResponseDTO = getResonseDTO();
			entityResponseDTO.buildResponse(entityOptional.get());
			JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}
	
	
	public JSONResponse<ResponseDTO> findByNaturalKey(RequestDTO requestDTO) {
		Example<E> example = requestDTO.buildExampleUsingNaturalKey();
 		updateEffectiveStatus(example.getProbe(), ACTIVE);
		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			ResponseDTO entityResponseDTO = getResonseDTO();
			entityResponseDTO.buildResponse(entityOptional.get());
			JSONResponse<ResponseDTO> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityResponseDTO);
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}
	

	public JSONResponse<List<ResponseDTO>> listEntities() {
		List<E> entities = repository.findAll();
		List<ResponseDTO> entityResponseDTOs = new ArrayList<>(entities.size());
		entities.stream().forEach(entity -> {
			ResponseDTO newDTO = getResonseDTO();
			newDTO.buildResponse(entity);
			entityResponseDTOs.add(newDTO);
		});
		JSONResponse<List<ResponseDTO>> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK,
				entityResponseDTOs);
		return response;
	}
	
	public JSONResponse<List<ResponseDTO>> listActiveEntities() {
		RequestDTO requestDTO = getRequestDTO();
		Example<E> example = requestDTO.buildExampleUsingNaturalKey();
 		updateEffectiveStatus(example.getProbe(), ACTIVE);
		List<E> entities = repository.findAll(example);
		List<ResponseDTO> entityResponseDTOs = new ArrayList<>(entities.size());
		entities.stream().forEach(entity -> {
			ResponseDTO newDTO = getResonseDTO();
			newDTO.buildResponse(entity);
			entityResponseDTOs.add(newDTO);
		});
		JSONResponse<List<ResponseDTO>> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK,
				entityResponseDTOs);
		return response;
	}

	public abstract RequestDTO getRequestDTO();

	public JSONResponse<String> deleteEntity(K key) {
		Optional<E> entityOptional = repository.findById(key);
		if (entityOptional.isPresent()) {
			repository.deleteById(key);
			JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");

			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}
	
	
	public JSONResponse<String> deleteEntityByNaturalKey(RequestDTO requestDTO) {
		Example<E> example = requestDTO.buildExampleUsingNaturalKey();
 		updateEffectiveStatus(example.getProbe(), ACTIVE);
		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			repository.delete(entityOptional.get());
			JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
			return response;
		} else {
			throw new ResourceNotFoundException();
		} 		
	}

}
