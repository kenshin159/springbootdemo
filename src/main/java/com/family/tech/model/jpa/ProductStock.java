package com.family.tech.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "product_stock")
public class ProductStock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2687004758065208889L;

	@Id
	@GeneratedValue
	@Column(name = "Product_Stock_Id")
	private Long productStockId;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonBackReference
	private Product product;

	@ManyToOne
	@JoinColumn(name = "stock_id")
	@JsonBackReference
	private Stock stock;

	private int amount;

	public ProductStock() {
		super();
	}

	public Long getProductStockId() {
		return productStockId;
	}

	public void setProductStockId(Long productStockId) {
		this.productStockId = productStockId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		return result;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ProductStock that = (ProductStock) o;

		if (this.product.getProductId().longValue() == that.product.getProductId().longValue()
				&& this.stock.getStockId().longValue() == that.stock.getStockId().longValue()) {
			return true;
		}
		return false;
	}

}
