package com.family.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.family.tech.model.jpa.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
	// @Query("select sum(ps.amount) from product_stock ps where ps.product_id in
	// (select product_id from product p where p.product_code = :productCode)")
	// int getTotalProductByProductCode(@Param("productCode") String productCode);
	Stock findByStockCode(String stockCode);
}
