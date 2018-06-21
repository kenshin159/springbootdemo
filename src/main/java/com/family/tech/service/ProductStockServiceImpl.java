package com.family.tech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.family.tech.model.jpa.ProductStock;
import com.family.tech.repository.ProductStockRepository;

@Component
public class ProductStockServiceImpl implements ProductStockService {
	@Autowired
	private ProductStockRepository productStockRepository;

	@Override
	public List<ProductStock> findByProductIdAndStockId(Long productId, Long stockId) {
		return productStockRepository.findByProductIdAndStockId(productId, stockId);
	}

	@Override
	public ProductStock findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductStock> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductStock save(ProductStock object) {
		return productStockRepository.save(object);
		// return
		// productStockRepository.updateProductStock(object.getProduct().getProductId(),
		// object.getStock().getStockId(), object.getAmount());
	}

	@Override
	public List<ProductStock> save(List<ProductStock> objects) {
		return productStockRepository.save(objects);
	}

	@Override
	public void delete(ProductStock object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(List<ProductStock> objects) {
		// TODO Auto-generated method stub

	}

}
