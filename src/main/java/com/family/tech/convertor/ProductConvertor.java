package com.family.tech.convertor;

import com.family.tech.form.ProductForm;
import com.family.tech.model.jpa.Product;

public class ProductConvertor {
	public static Product convertProductFormToProduct(ProductForm productForm) {
		Product product = new Product(productForm.getProductCode(), productForm.getProductName());
		product.setPrice(productForm.getPrice());
		product.setOrigin(productForm.getOrigin());
		product.setDetail(productForm.getDetail());
		return product;
	}

	public static ProductForm convertProductToProductForm(Product product) {
		ProductForm productForm = new ProductForm();
		productForm.setProductId(product.getProductId());
		productForm.setProductCode(product.getProductCode());
		productForm.setProductName(product.getProductName());
		productForm.setPrice(product.getPrice());
		productForm.setOrigin(product.getOrigin());
		productForm.setDetail(product.getDetail());
		return productForm;
	}
}
