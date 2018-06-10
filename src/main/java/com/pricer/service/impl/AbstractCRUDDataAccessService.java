package com.pricer.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public abstract class AbstractCRUDDataAccessService<E, K, R extends JpaRepository<E, K>>
		implements DataAccessService<E, K, R> {

	@Autowired
	R repository;

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractCRUDDataAccessService.class);

	protected abstract void setKey(E request, K key);

	
	
	@Transactional
	public JSONResponse<E> addEntity(E request) {
		try {
			E createdEntity = repository.save(request);
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, createdEntity);
			return response;
		} catch (Exception e) {
			LOGGER.error("addEntity failed.", e);
			throw e;
		}
	}

	@Transactional
	public JSONResponse<E> putEntity(E request, K key) {
		try {
			setKey(request, key);
			E updatedEntity = repository.save(request);
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, updatedEntity);
			return response;
		} catch (Exception e) {
			LOGGER.error("putEntity failed.", e);
			throw e;
		}
	}

	@Transactional
	public JSONResponse<E> putEntityByExample(E request, E filters) {
		try {
			E updatedEntity = repository.save(request);
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, updatedEntity);
			return response;
		} catch (Exception e) {
			LOGGER.error("putEntityByExample failed.", e);
			throw e;
		}
	}

	
	public JSONResponse<E> findEntity(K key) {
		try {
			Optional<E> entityOptional = repository.findById(key);
			if (entityOptional.isPresent()) {
				JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityOptional.get());
				return response;
			} else {
				throw new ResourceNotFoundException();
			}
		} catch (Exception e) {
			LOGGER.error("findEntity failed.", e);
			throw e;
		}
	}

	public JSONResponse<E> findEntityByExample(E request) {
		try {
			Example<E> example = Example.of(request);
			Optional<E> entityOptional = repository.findOne(example);
			if (entityOptional.isPresent()) {
				JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entityOptional.get());
				return response;
			} else {
				throw new ResourceNotFoundException();
			}
		} catch (Exception e) {
			LOGGER.error("findEntityByExample failed.", e);
			throw e;
		}
	}

	public JSONResponse<List<E>> listEntities() {
		List<E> entities = repository.findAll();
		JSONResponse<List<E>> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entities);
		return response;
	}

	@Transactional
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
		} catch (DataIntegrityViolationException e) {
			throw new DependencyPresentException();
		} catch (Exception e) {
			LOGGER.error("deleteEntity failed.", e);
			throw e;
		}

	}

	@Transactional
	public JSONResponse<String> deleteEntityByExample(E request) {
		try {
			Example<E> example = Example.of(request);
			Optional<E> entityOptional = repository.findOne(example);
			if (entityOptional.isPresent()) {
				repository.delete(entityOptional.get());
				JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
				return response;
			} else {
				throw new ResourceNotFoundException();
			}
		} catch (Exception e) {
			LOGGER.error("deleteEntityByExample failed.", e);
			throw e;
		}
	}
}
