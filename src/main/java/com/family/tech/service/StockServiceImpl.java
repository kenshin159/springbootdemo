package com.family.tech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.family.tech.model.jpa.Stock;
import com.family.tech.repository.StockRepository;

@Component
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepository;

	@Override
	public Stock findById(Long id) {
		return stockRepository.findOne(id);
	}

	@Override
	public List<Stock> findAll() {
		return stockRepository.findAll();
	}

	@Override
	public Stock save(Stock object) {
		return stockRepository.save(object);
	}

	@Override
	public List<Stock> save(List<Stock> objects) {
		return stockRepository.save(objects);
	}

	@Override
	public void delete(Stock objects) {
		stockRepository.delete(objects);
	}

	@Override
	public void delete(List<Stock> objects) {
		stockRepository.delete(objects);
	}

	@Override
	public Stock findByStockCode(String stockCode) {
		return stockRepository.findByStockCode(stockCode);
	}

}
