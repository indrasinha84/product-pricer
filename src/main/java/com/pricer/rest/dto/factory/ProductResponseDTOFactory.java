package com.pricer.rest.dto.factory;

import org.springframework.stereotype.Component;

import com.pricer.rest.dto.DTOFactory;
import com.pricer.rest.dto.ProductResponseDTO;

@Component
public class ProductResponseDTOFactory implements DTOFactory<ProductResponseDTO> {

	@Override
	public ProductResponseDTO getResonseDTO() {
		return new ProductResponseDTO();
	}
}
