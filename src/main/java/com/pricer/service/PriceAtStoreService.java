package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.entity.PriceAtStore;
import com.pricer.repository.PriceAtStoreRepository;
import com.pricer.rest.dto.PriceAtStoreRequestDTO;
import com.pricer.rest.dto.PriceAtStoreResponseDTO;

@Service
public class PriceAtStoreService extends
		AbstractDataAccessService<PriceAtStore, Integer, PriceAtStoreResponseDTO, PriceAtStoreRequestDTO, PriceAtStoreRepository> {

	@Override
	public PriceAtStoreResponseDTO getResonseDTO() {
		return new PriceAtStoreResponseDTO();
	}

	@Override
	public void updateEffectiveStatus(PriceAtStore old, String status) {
		old.setEffectivestatus(status);
	}

	@Override
	public PriceAtStoreRequestDTO getRequestDTO() {
		return new PriceAtStoreRequestDTO();

	} 
}
