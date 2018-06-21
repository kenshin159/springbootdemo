package com.family.tech.form;

import com.family.tech.model.jpa.Product;

public class ProductForm extends Product {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2264749661847022075L;
	private String fromPrice;
	private String toPrice;
	private Long stockId;
	private String number;

	public String getFromPrice() {
		return fromPrice;
	}

	public void setFromPrice(String fromPrice) {
		this.fromPrice = fromPrice;
	}

	public String getToPrice() {
		return toPrice;
	}

	public void setToPrice(String toPrice) {
		this.toPrice = toPrice;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
