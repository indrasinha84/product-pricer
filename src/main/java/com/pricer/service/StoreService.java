package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.entity.Store;
import com.pricer.repository.StoreRepository;
import com.pricer.rest.dto.StoreRequestDTO;
import com.pricer.rest.dto.StoreResponseDTO;

@Service
public class StoreService
		extends AbstractDataAccessService<Store, Integer, StoreResponseDTO, StoreRequestDTO, StoreRepository> {

	@Override
	public StoreResponseDTO getResonseDTO() {
		return new StoreResponseDTO();
	}
}
