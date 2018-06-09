package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pricer.model.MarketPrice;

@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Integer> {

	@Query("SELECT MAX(M.id) FROM MarketPrice M WHERE M.effectiveStatus = 'A'")
	Integer getMaxPriceCollected();

}
