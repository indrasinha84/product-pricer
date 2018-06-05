package com.pricer.entity.id.generator;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import com.pricer.entity.Product;

public class ProductIdGenerator extends SequenceStyleGenerator {
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		if (((Product) object).getId() != null) {
			return ((Product) object).getId();
		};
		return super.generate(session, object);
	}
	
	
}
