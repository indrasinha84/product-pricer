package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricer.model.StorePrice;

@Repository
public interface StorePriceRepository extends JpaRepository<StorePrice, Integer>  {

}
