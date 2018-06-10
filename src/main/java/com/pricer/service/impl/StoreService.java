package com.pricer.service.impl;

import org.springframework.stereotype.Service;

import com.pricer.model.Store;
import com.pricer.repository.StoreRepository;

@Service
public class StoreService extends AbstractCRUDDataAccessService<Store, Integer, StoreRepository> {

	@Override
	protected void setKey(Store request, Integer key) {
		request.setId(key);
	}

}
