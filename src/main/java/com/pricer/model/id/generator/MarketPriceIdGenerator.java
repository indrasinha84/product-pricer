package com.pricer.model.id.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.pricer.model.MarketPrice;
import com.pricer.rest.exception.IdNotAllowedException;

public class MarketPriceIdGenerator extends SequenceStyleGenerator {
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Integer generatedId = (Integer) super.generate(session, object);
		Integer inputId = ((MarketPrice) object).getId();
		if (inputId != null && inputId > generatedId) {
			throw new IdNotAllowedException("Price", "id", inputId);
		} else if (inputId != null) {
			return inputId;
		}
		return generatedId;
	}
	
	
}
