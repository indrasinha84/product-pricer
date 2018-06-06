package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricer.entity.PriceAtStore;

@Repository
public interface PriceAtStoreRepository extends JpaRepository<PriceAtStore, Integer> {

}
