package com.pricer.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pricer.model.JSONResponse;

public interface DataAccessService<E, K, R extends JpaRepository<E, K>> {

	public JSONResponse<E> addEntity(E request);

	public JSONResponse<E> putEntity(E request, K key);
	
	public JSONResponse<E> putEntityByExample(E request, E filters);

	public JSONResponse<E> findEntity(K key);

	public JSONResponse<E> findEntityByExample(E filters);

	public JSONResponse<List<E>> listEntities();

	public JSONResponse<String> deleteEntity(K key);

	public JSONResponse<String> deleteEntityByExample(E filters);
}
