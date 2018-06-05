package com.pricer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pricer.entity.sequence.ProductWithSequence;

@Repository
public interface ProductWithSequenceRepository extends JpaRepository<ProductWithSequence, Integer>  {

}
