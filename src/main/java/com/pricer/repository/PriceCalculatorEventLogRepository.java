package com.pricer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pricer.model.PriceCalculatorEventLog;

@Repository
public interface PriceCalculatorEventLogRepository extends JpaRepository<PriceCalculatorEventLog, Integer> {

	@Query("SELECT MAX(E.endPosition) FROM PriceCalculatorEventLog E WHERE E.status != com.pricer.model.JobStatus.CANCELLED ")
	Integer getMaxLoggedPosition();

	@Query("SELECT E From PriceCalculatorEventLog E WHERE E.status "
			+ "NOT IN (com.pricer.model.JobStatus.FAILED, com.pricer.model.JobStatus.COMPLETED, com.pricer.model.JobStatus.CANCELLED) ORDER BY E.id")
	List<PriceCalculatorEventLog> getPendingTheadsInOrder();
}
