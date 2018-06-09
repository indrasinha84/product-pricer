package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pricer.model.PriceCalculatorEventLog;

@Repository
public interface PriceCalculatorEventLogRepository extends JpaRepository<PriceCalculatorEventLog, Integer> {

	
	@Query("SELECT MAX(E.endPosition) FROM PriceCalculatorEventLog E WHERE E.status != 'CANCELLED'")
	Integer getMaxLoggedPosition();
}
