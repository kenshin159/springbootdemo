package com.family.tech.service;

import com.family.tech.model.jpa.Stock;

public interface StockService extends CommonService<Stock, Long> {

	Stock findByStockCode(String stockCode);

}
