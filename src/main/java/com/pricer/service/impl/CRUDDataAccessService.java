package com.pricer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.pricer.model.JSONResponse;
import com.pricer.model.RESTMessage;
import com.pricer.rest.exception.DependencyPresentException;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.DataAccessService;

public abstract class CRUDDataAccessService<E, K, R extends JpaRepository<E, K>>
		implements DataAccessService<E, K, R> {

	@Autowired
	R repository;

	protected abstract void setKey(E request, K key);

	public JSONResponse<E> addEntity(E request) {
		E createdEntity = repository.save(request);
		JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, createdEntity);
		return response;
	}

	public JSONResponse<E> putEntity(E request, K key) {
		setKey(request, key);
		E updatedEntity = repository.save(request);
		JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, updatedEntity);
		return response;
	}

	public JSONResponse<E> putEntityByExample(E request, E filters) {
		E updatedEntity = repository.save(request);
		JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, updatedEntity);
		return response;
	}

	public JSONResponse<E> findEntity(K key) {
		Optional<E> entityOptional = repository.findById(key);
		if (entityOptional.isPresent()) {
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityOptional.get());
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public JSONResponse<E> findEntityByExample(E request) {
		Example<E> example = Example.of(request);
		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityOptional.get());
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public JSONResponse<List<E>> listEntities() {
		List<E> entities = repository.findAll();
		JSONResponse<List<E>> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entities);
		return response;
	}

	public JSONResponse<String> deleteEntity(K key) {
		try {
		Optional<E> entityOptional = repository.findById(key);
		if (entityOptional.isPresent()) {
			repository.deleteById(key);
			JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
		} catch(DataIntegrityViolationException e) {
			throw new DependencyPresentException();
		}
	}

	public JSONResponse<String> deleteEntityByExample(E request) {
		Example<E> example = Example.of(request);
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
