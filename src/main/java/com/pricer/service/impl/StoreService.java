package com.pricer.service.impl;

import com.pricer.model.Store;
import com.pricer.repository.StoreRepository;

public class StoreService extends AbstractCRUDDataAccessService<Store, Integer, StoreRepository> {

	@Override
	protected void setKey(Store request, Integer key) {
		request.setId(key);
	}

}
