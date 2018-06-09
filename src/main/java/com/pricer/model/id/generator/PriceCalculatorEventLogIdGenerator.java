package com.pricer.model.id.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.pricer.model.PriceCalculatorEventLog;
import com.pricer.rest.exception.IdNotAllowedException;

public class PriceCalculatorEventLogIdGenerator extends SequenceStyleGenerator {
	
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Integer generatedId = (Integer) super.generate(session, object);
		Integer inputId = ((PriceCalculatorEventLog) object).getId();
		if (inputId != null && inputId > generatedId) {
			throw new IdNotAllowedException("PriceCalculatorEventLog", "id", inputId);
		} else if (inputId != null) {
			return inputId;
		}
		return generatedId;
	}
	
	
}
