package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.model.Store;
import com.pricer.repository.StoreRepository;

@Service("storeCRUDService")
public class StoreService
		extends CRUDDataAccessService<Store, Integer, StoreRepository> {

	@Override
	protected void setKey(Store request, Integer key) {
		request.setId(key);			
	}

}
