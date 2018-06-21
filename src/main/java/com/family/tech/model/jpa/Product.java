package com.family.tech.model.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1659897443499524787L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	@NotNull
	@Size(max = 8)
	@Column(unique = true)
	private String productCode;

	@NotNull
	@Size(max = 50)
	private String productName;

	private Double price;

	@Size(max = 50)
	private String origin;

	@Size(max = 256)
	private String detail;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<ProductStock> productStocks = new HashSet<ProductStock>();

	public Product() {
		super();
	}

	public Product(String productCode, String productName) {
		super();
		this.productCode = productCode;
		this.productName = productName;
	}

	public void addProductStock(ProductStock productStock) {
		this.productStocks.add(productStock);
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Set<ProductStock> getProductStocks() {
		return productStocks;
	}

	public void setProductStocks(Set<ProductStock> productStocks) {
		this.productStocks = productStocks;
	}

}
