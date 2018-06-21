package com.family.tech.service;

import java.util.List;
import java.util.Map;

import com.family.tech.model.jpa.Product;

public interface ProductService extends CommonService<Product, Long> {

	/**
	 * get total products in all stock with productCode
	 * 
	 * @param productCode
	 * @return
	 */
	Integer getTotalProductByProductCode(String productCode);

	/**
	 * find all product with condition productCode and productName
	 * 
	 * @param productCode
	 * @param productName
	 * @return
	 */
	List<Product> findByProductCodeLikeAndProductNameLike(String productCode, String productName);

	/**
	 * search product. keys of mapCondition in (ProductField and PageField)
	 * 
	 * @param mapCondition
	 * @return
	 */
	List<Product> searchProduct(Map<String, Object> mapCondition);

	/**
	 * get total record with condition search. Keys of mapCondition in
	 * (ProductField)
	 * 
	 * @param mapCondition
	 * @return
	 */
	Long countSearchProduct(Map<String, Object> mapCondition);

	/**
	 * Find product with productCode
	 * 
	 * @param productCode
	 * @return
	 */
	Product findByProductCode(String productCode);

	/**
	 * check ProductCode exists and productId is not equal.(use for update product)
	 * 
	 * @param productCode
	 * @param productId
	 * @return
	 */
	boolean checkProductByProductCodeNeProductId(String productCode, Long productId);

}
