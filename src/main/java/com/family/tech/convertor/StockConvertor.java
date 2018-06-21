package com.family.tech.convertor;

import com.family.tech.form.StockForm;
import com.family.tech.model.jpa.Stock;

public class StockConvertor {
	public static Stock convertStockFormToStock(StockForm form) {
		Stock stock = new Stock(form.getStockCode(), form.getStockName());
		stock.setAddress(form.getAddress());
		return stock;
	}
}
