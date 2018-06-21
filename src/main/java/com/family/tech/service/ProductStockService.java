package com.family.tech.service;

import java.util.List;

import com.family.tech.model.jpa.ProductStock;

public interface ProductStockService extends CommonService<ProductStock, Long> {

	List<ProductStock> findByProductIdAndStockId(Long productId, Long stockId);
}
