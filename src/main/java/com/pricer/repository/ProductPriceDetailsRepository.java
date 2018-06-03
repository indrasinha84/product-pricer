package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricer.model.ProductPriceDetails;

@Repository
public interface ProductPriceDetailsRepository extends JpaRepository<ProductPriceDetails, Integer> {

}
