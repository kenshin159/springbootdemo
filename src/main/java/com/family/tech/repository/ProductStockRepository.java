package com.family.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.family.tech.model.jpa.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, ProductStock> {

	@Query(value = "select * from product_stock ps where ps.product_id = :productId and ps.stock_id = :stockId",
			nativeQuery = true)
	List<ProductStock> findByProductIdAndStockId(@Param("productId") Long productId, @Param("stockId") Long stockId);

	@Query(value = "update product_stock ps set ps.amount = :amount where ps.product_id = :productId and ps.stock_id = :stockId",
			nativeQuery = true)
	ProductStock updateProductStock(@Param("productId") Long productId, @Param("stockId") Long stockId,
			@Param("amount") int amount);

}
