package com.pricer.entity.id.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.pricer.entity.Product;
import com.pricer.rest.exception.IdNotAllowedException;

public class PriceAtStoreIdGenerator extends SequenceStyleGenerator {
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Integer generatedId = (Integer) super.generate(session, object);
		Integer inputId = ((Product) object).getId();
		if (inputId != null && inputId > generatedId) {
			throw new IdNotAllowedException("Price", "id", inputId);
		} else if (inputId != null) {
			return inputId;
		}
		return generatedId;
	}
	
	
}
