package com.pricer.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import com.pricer.model.EffectiveStatus;
import com.pricer.model.JSONResponse;
import com.pricer.model.RESTMessage;
import com.pricer.rest.exception.ResourceAlreadyExists;
import com.pricer.rest.exception.ResourceNotFoundException;
import com.pricer.service.DataAccessService;

public abstract class AbstractSoftDataAccessService<E, K, R extends JpaRepository<E, K>>
		implements DataAccessService<E, K, R> {

	@Autowired
	R repository;

	protected abstract void setEffectiveStatus(E entity, EffectiveStatus effectiveStatus);

	protected abstract E getEntityInstance();

	protected abstract E setNaturalKey(E request);

	public JSONResponse<E> addEntity(E request) {
		E lookup = setNaturalKey(request);
		setEffectiveStatus(lookup, EffectiveStatus.A);
		Example<E> example = Example.of(lookup);
		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			throw new ResourceAlreadyExists();
		} else {
			setEffectiveStatus(request, EffectiveStatus.A);
			E createdEntity = repository.save(request);
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, createdEntity);
			return response;
		}

	}

	public JSONResponse<E> putEntity(E request, K key) {
		Optional<E> entityOptional = repository.findById(key);
		if (entityOptional.isPresent()) {
			E old = entityOptional.get();
			setEffectiveStatus(old, EffectiveStatus.I);
			repository.save(old);
		}
		setEffectiveStatus(request, EffectiveStatus.A);
		E updatedEntity = repository.save(request);
		JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, updatedEntity);
		return response;
	}

	public JSONResponse<E> putEntityByExample(E request, E filters) {
		setEffectiveStatus(filters, EffectiveStatus.A);
		Example<E> example = Example.of(filters);
		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			E old = entityOptional.get();
			setEffectiveStatus(old, EffectiveStatus.I);
			repository.save(old);
		}
		setEffectiveStatus(request, EffectiveStatus.A);
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

	public JSONResponse<E> findEntityByExample(E filters) {
		setEffectiveStatus(filters, EffectiveStatus.A);
		Example<E> example = Example.of(filters);
		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityOptional.get());
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public JSONResponse<List<E>> listEntities() {
		E request = getEntityInstance();
		setEffectiveStatus(request, EffectiveStatus.A);
		Example<E> example = Example.of(request);
		List<E> entities = repository.findAll(example);
		JSONResponse<List<E>> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entities);
		return response;
	}

	public JSONResponse<String> deleteEntity(K key) {
		Optional<E> entityOptional = repository.findById(key);
		if (entityOptional.isPresent()) {
			E old = entityOptional.get();
			setEffectiveStatus(old, EffectiveStatus.I);
			repository.save(old);
			JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}

	public JSONResponse<String> deleteEntityByExample(E filters) {
		setEffectiveStatus(filters, EffectiveStatus.A);
		Example<E> example = Example.of(filters);
		Optional<E> entityOptional = repository.findOne(example);
		if (entityOptional.isPresent()) {
			E old = entityOptional.get();
			setEffectiveStatus(old, EffectiveStatus.I);
			repository.save(old);
			JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
			return response;
		} else {
			throw new ResourceNotFoundException();
		}
	}
}
