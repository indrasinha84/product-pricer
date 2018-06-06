package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.entity.PriceDetails;
import com.pricer.repository.PriceDetailsRepository;
import com.pricer.rest.dto.PriceDetailsRequestDTO;
import com.pricer.rest.dto.PriceDetailsResponseDTO;

@Service
public class PriceDetailsService
		extends AbstractDataAccessService<PriceDetails, Integer, PriceDetailsResponseDTO, PriceDetailsRequestDTO, PriceDetailsRepository> {

	@Override
	public PriceDetailsResponseDTO getResonseDTO() {
		return new PriceDetailsResponseDTO();
	}

	@Override
	public void updateEffectiveStatus(PriceDetails old, String string) {
		
	}

	@Override
	public PriceDetailsRequestDTO getRequestDTO() {
		return new PriceDetailsRequestDTO();
	}
}
