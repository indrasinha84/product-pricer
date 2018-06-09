package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricer.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer>  {

}
