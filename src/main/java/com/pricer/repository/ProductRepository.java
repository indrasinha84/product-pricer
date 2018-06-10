package com.pricer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pricer.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT P FROM Product P WHERE P.id IN (SELECT M.product.id From com.pricer.model.MarketPrice M WHERE M.effectiveStatus != com.pricer.model.EffectiveStatus.INACTIVE "
			+ " AND M.id BETWEEN :chunkStartPosition AND :chunkEndPosition)")
	List<Product> getProductsForPriceCalculation(@Param("chunkStartPosition") Integer chunkStartPosition,
			@Param("chunkEndPosition") Integer chunkEndPosition);
}
