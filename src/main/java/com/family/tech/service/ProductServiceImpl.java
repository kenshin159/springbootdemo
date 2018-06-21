package com.family.tech.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.family.tech.constant.PageField;
import com.family.tech.constant.ProductField;
import com.family.tech.model.jpa.Product;
import com.family.tech.repository.ProductRepository;

@Component
public class ProductServiceImpl extends BaseService implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product findById(Long id) {
		return productRepository.getOne(id);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Secured("ROLE_ADMIN")
	@Override
	public Product save(Product t) {
		return productRepository.save(t);
	}

	@Secured("ROLE_ADMIN")
	@Override
	public List<Product> save(List<Product> list) {
		return productRepository.save(list);
	}

	@Secured("ROLE_ADMIN")
	@Override
	public void delete(Product t) {
		productRepository.delete(t);
	}

	@Secured("ROLE_ADMIN")
	@Override
	public void delete(List<Product> t) {
		productRepository.delete(t);
	}

	@Override
	public Integer getTotalProductByProductCode(String productCode) {
		return productRepository.getTotalProductByProductCode(productCode);
	}

	@Override
	public List<Product> findByProductCodeLikeAndProductNameLike(String productCode, String productName) {
		return productRepository.findByProductCodeLikeAndProductNameLike(productCode, productName);
	}

	/**
	 * Search product with map condition
	 */
	@Override
	public List<Product> searchProduct(Map<String, Object> mapCondition) {
		Session session = getCurrentSession();
		Criteria criteria = buildCriteriaSearch(session, mapCondition);
		if (mapCondition.containsKey(PageField.PAGE_SIZE)) {
			criteria.setMaxResults((Integer) mapCondition.get(PageField.PAGE_SIZE));
		}
		if (mapCondition.containsKey(PageField.START_ROW)) {
			criteria.setFirstResult((Integer) mapCondition.get(PageField.START_ROW));
		}
		List list = criteria.list();
		return list;
	}

	/**
	 * Calculate total record when searching product with map condition
	 */
	@Override
	public Long countSearchProduct(Map<String, Object> mapCondition) {
		Session session = getCurrentSession();
		Criteria criteria = buildCriteriaSearch(session, mapCondition);
		criteria.setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		return count;
	}

	/*
	 * (non-Javadoc) Check productCode existed
	 * 
	 * @see com.nash.tech.service.ProductService#findByProductCode(java.lang.String)
	 */
	@Override
	public Product findByProductCode(String productCode) {
		return productRepository.findTopByProductCode(productCode);
	}

	/*
	 * (non-Javadoc) check productCode existed when update
	 * 
	 * @see
	 * com.nash.tech.service.ProductService#countProductByProductCodeNeProductId(
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public boolean checkProductByProductCodeNeProductId(String productCode, Long productId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq(ProductField.PRODUCT_CODE, productCode));
		criteria.add(Restrictions.ne(ProductField.PRODUCT_ID, productId.longValue()));
		if (criteria.list().size() > 0) {
			return true;
		}
		return false;
	}

	private Criteria buildCriteriaSearch(Session session, Map<String, Object> mapCondition) {
		Criteria criteria = session.createCriteria(Product.class);
		if (mapCondition.containsKey(ProductField.PRODUCT_CODE)) {
			criteria.add(Restrictions.like(ProductField.PRODUCT_CODE,
					mapCondition.get(ProductField.PRODUCT_CODE).toString(), MatchMode.ANYWHERE).ignoreCase());
		}
		if (mapCondition.containsKey(ProductField.PRODUCT_NAME)) {
			criteria.add(Restrictions.like(ProductField.PRODUCT_NAME,
					mapCondition.get(ProductField.PRODUCT_NAME).toString(), MatchMode.ANYWHERE).ignoreCase());
		}
		if (mapCondition.containsKey(ProductField.FROM_PRICE)) {
			criteria.add(Restrictions.ge(ProductField.PRICE, mapCondition.get(ProductField.FROM_PRICE)));
		}
		if (mapCondition.containsKey(ProductField.TO_PRICE)) {
			criteria.add(Restrictions.le(ProductField.PRICE, mapCondition.get(ProductField.TO_PRICE)));
		}
		if (mapCondition.containsKey(ProductField.ORIGIN)) {
			criteria.add(Restrictions
					.like(ProductField.ORIGIN, mapCondition.get(ProductField.ORIGIN).toString(), MatchMode.ANYWHERE)
					.ignoreCase());
		}
		criteria.addOrder(Order.asc(ProductField.PRODUCT_ID));
		return criteria;
	}

}
