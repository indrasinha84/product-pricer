package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricer.entity.PriceDetails;

@Repository
public interface PriceDetailsRepository extends JpaRepository<PriceDetails, Integer>  {

}
