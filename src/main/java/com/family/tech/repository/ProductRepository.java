package com.family.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.family.tech.model.jpa.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "select sum(ps.amount) from product_stock ps where ps.product_id in (select product_id from product p where p.product_code = :productCode)",
			nativeQuery = true)
	Integer getTotalProductByProductCode(@Param("productCode") String productCode);

	List<Product> findByProductCodeLikeAndProductNameLike(String productCode, String productName);

	Product findTopByProductCode(String productCode);

}
