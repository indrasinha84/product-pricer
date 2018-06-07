package com.pricer.service;

import org.springframework.stereotype.Service;

import com.pricer.entity.MarketPrice;
import com.pricer.repository.MarketPriceRepository;
import com.pricer.rest.dto.MarketPriceRequestDTO;
import com.pricer.rest.dto.MarketPriceResponseDTO;

@Service
public class MarketPriceService extends
		AbstractDataAccessService<MarketPrice, Integer, MarketPriceResponseDTO, MarketPriceRequestDTO, MarketPriceRepository> {

	@Override
	public MarketPriceResponseDTO getResonseDTO() {
		return new MarketPriceResponseDTO();
	}

	@Override
	public void updateEffectiveStatus(MarketPrice old, String status) {
		old.setEffectivestatus(status);
	}

	@Override
	public MarketPriceRequestDTO getRequestDTO() {
		return new MarketPriceRequestDTO();

	} 
}
