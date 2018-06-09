package com.pricer.entity.id.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.pricer.model.PriceDetails;
import com.pricer.rest.exception.IdNotAllowedException;

public class PriceDetailsIdGenerator extends SequenceStyleGenerator {
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Integer generatedId = (Integer) super.generate(session, object);
		Integer inputId = ((PriceDetails) object).getId();
		if (inputId != null && inputId > generatedId) {
			throw new IdNotAllowedException("PriceDetails", "id", inputId);
		} else if (inputId != null) {
			return inputId;
		}
		return generatedId;
	}
	
	
}
