package com.family.tech.model.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5570490870751761539L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stockId;

	@NotNull
	@NotEmpty
	@Size(max = 8)
	@Column(unique = true)
	private String stockCode;

	@NotNull
	@NotEmpty
	@Size(max = 50)
	private String stockName;

	@Size(max = 256)
	private String address;

	@OneToMany(mappedBy = "stock")
	@JsonManagedReference
	private Set<ProductStock> productStocks = new HashSet<ProductStock>();

	public Stock() {
		super();
	}

	public Stock(String stockCode, String stockName) {
		super();
		this.stockCode = stockCode;
		this.stockName = stockName;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<ProductStock> getProductStocks() {
		return productStocks;
	}

	public void setProductStocks(Set<ProductStock> productStocks) {
		this.productStocks = productStocks;
	}

}
