package com.pricer.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger LOGGER = LoggerFactory.getLogger(AbstractSoftDataAccessService.class);

	protected abstract void setEffectiveStatus(E entity, EffectiveStatus effectiveStatus);

	protected abstract E getEntityInstance();

	protected abstract E setNaturalKey(E request);

	public JSONResponse<E> addEntity(E request) {
		try {
			E lookup = setNaturalKey(request);
			setEffectiveStatus(lookup, EffectiveStatus.ACTIVE);
			Example<E> example = Example.of(lookup);
			Optional<E> entityOptional = repository.findOne(example);
			if (entityOptional.isPresent()) {
				throw new ResourceAlreadyExists();
			} else {
				setEffectiveStatus(request, EffectiveStatus.ACTIVE);
				E createdEntity = repository.save(request);
				JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, createdEntity);
				return response;
			}
		} catch (Exception e) {
			LOGGER.error("addEntity failed.", e);
			throw e;
		}

	}

	public JSONResponse<E> putEntity(E request, K key) {
		try {
			Optional<E> entityOptional = repository.findById(key);
			if (entityOptional.isPresent()) {
				E old = entityOptional.get();
				setEffectiveStatus(old, EffectiveStatus.INACTIVE);
				repository.save(old);
			}
			setEffectiveStatus(request, EffectiveStatus.ACTIVE);
			E updatedEntity = repository.save(request);
			JSONResponse<E> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, updatedEntity);
			return response;
		} catch (Exception e) {
			LOGGER.error("putEntity failed.", e);
			throw e;
		}
	}

	public JSONResponse<E> putEntityByExample(E request, E filters) {
		try {
			setEffectiveStatus(filters, EffectiveStatus.ACTIVE);
			Example<E> example = Example.of(filters);
			Optional<E> entityOptional = repository.findOne(example);
			if (entityOptional.isPresent()) {
				E old = entityOptional.get();
				setEffectiveStatus(old, EffectiveStatus.INACTIVE);
				repository.save(old);
			}
			setEffectiveStatus(request, EffectiveStatus.ACTIVE);
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

	public JSONResponse<E> findEntityByExample(E filters) {
		try {
			setEffectiveStatus(filters, EffectiveStatus.ACTIVE);
			Example<E> example = Example.of(filters);
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
		try {
			E request = getEntityInstance();
			setEffectiveStatus(request, EffectiveStatus.ACTIVE);
			Example<E> example = Example.of(request);
			List<E> entities = repository.findAll(example);
			JSONResponse<List<E>> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, entities);
			return response;
		} catch (Exception e) {
			LOGGER.error("listEntities failed.", e);
			throw e;
		}
	}

	public JSONResponse<String> deleteEntity(K key) {
		try {
			Optional<E> entityOptional = repository.findById(key);
			if (entityOptional.isPresent()) {
				E old = entityOptional.get();
				setEffectiveStatus(old, EffectiveStatus.INACTIVE);
				repository.save(old);

				E deletedRow = copyEntityForDelete(old);
				setEffectiveStatus(deletedRow, EffectiveStatus.DELETED);
				repository.save(deletedRow);
				JSONResponse<String> response = new JSONResponse<>(HttpStatus.OK, RESTMessage.OK, "");
				return response;
			} else {
				throw new ResourceNotFoundException();
			}
		} catch (Exception e) {
			LOGGER.error("deleteEntity(K key) failed.", e);
			throw e;
		}
	}

	public JSONResponse<String> deleteEntityByExample(E filters) {
		try {
			setEffectiveStatus(filters, EffectiveStatus.ACTIVE);
			Example<E> example = Example.of(filters);
			Optional<E> entityOptional = repository.findOne(example);
			if (entityOptional.isPresent()) {
				E old = entityOptional.get();
				setEffectiveStatus(old, EffectiveStatus.INACTIVE);
				repository.save(old);

				E deletedRow = copyEntityForDelete(old);
				setEffectiveStatus(deletedRow, EffectiveStatus.DELETED);
				repository.save(deletedRow);
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

	protected abstract E copyEntityForDelete(E old);
}
