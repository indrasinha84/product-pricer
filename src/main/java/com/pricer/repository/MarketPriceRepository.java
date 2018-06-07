package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricer.entity.MarketPrice;

@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Integer> {

}
