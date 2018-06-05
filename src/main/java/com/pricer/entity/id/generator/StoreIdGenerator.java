package com.pricer.entity.id.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.pricer.entity.Store;
import com.pricer.rest.exception.IdNotAllowedException;

public class StoreIdGenerator extends SequenceStyleGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException, RuntimeException {
		Integer generatedId = (Integer) super.generate(session, object);
		Integer inputId = ((Store) object).getId();
		if (inputId != null && inputId > generatedId) {
			throw new IdNotAllowedException("Store", "id", inputId);
		} else if (inputId != null) {
			return inputId;
		}

		return generatedId;
	}

}
