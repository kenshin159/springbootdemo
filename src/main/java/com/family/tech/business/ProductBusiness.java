package com.family.tech.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.family.tech.constant.AlertType;
import com.family.tech.constant.ProductField;
import com.family.tech.form.ProductForm;
import com.family.tech.model.jpa.Product;
import com.family.tech.model.jpa.ProductStock;
import com.family.tech.model.jpa.Stock;
import com.family.tech.pojo.common.AlertDetail;
import com.family.tech.service.ProductService;
import com.family.tech.service.ProductStockService;
import com.family.tech.service.StockService;
import com.family.tech.utils.AlertFactory;

@Component
public class ProductBusiness {

	@Autowired
	private ProductService productService;

	@Autowired
	private StockService stockService;

	@Autowired
	private ProductStockService productStockService;

	@Autowired
	private MessageSource messageSource;

	public List<AlertDetail> insertProduct(ProductForm productForm, Locale locale) {
		List<AlertDetail> result = new ArrayList<AlertDetail>();
		result = checkConditionInsert(productForm, locale);
		if (result.isEmpty()) {
			Product product = createProduct(productForm);
			productService.save(product);
		}
		return result;
	}

	public List<AlertDetail> updateProduct(ProductForm productForm, Locale locale) {
		List<AlertDetail> result = new ArrayList<AlertDetail>();
		result = checkConditionUpdate(productForm, locale);
		if (result.isEmpty()) {
			Product product = createProduct(productForm);
			productService.save(product);
		}
		return result;
	}

	public List<AlertDetail> onWarehousing(ProductForm productForm, Locale locale) {
		List<AlertDetail> result = new ArrayList<AlertDetail>();
		result = checkNumber(productForm, locale);
		if (result.isEmpty()) {
			int number = Integer.parseInt(productForm.getNumber());
			List<ProductStock> listProductStock = productStockService
					.findByProductIdAndStockId(productForm.getProductId(), productForm.getStockId());
			if (listProductStock != null && !listProductStock.isEmpty()) {// update
				ProductStock productStock = listProductStock.get(0);
				productStock.setAmount(number);
				productStockService.save(productStock);
			} else {// create
				Product product = productService.findById(productForm.getProductId());
				Stock stock = stockService.findById(productForm.getStockId());

				ProductStock productStock = new ProductStock();
				productStock.setProduct(product);
				productStock.setStock(stock);
				productStock.setAmount(number);

				productStockService.save(productStock);
			}
		}
		return result;
	}

	private List<AlertDetail> checkNumber(ProductForm productForm, Locale locale) {
		List<AlertDetail> result = new ArrayList<AlertDetail>();
		if (productForm.getNumber() == null || productForm.getNumber().isEmpty()) {
			result.add(AlertFactory.generateAlertDetail("number.null", AlertType.ERRORS, messageSource, locale));
		} else {
			try {
				Integer.parseInt(productForm.getNumber());
			} catch (Exception e) {
				result.add(AlertFactory.generateAlertDetail("number.must.be.integer", AlertType.ERRORS, messageSource,
						locale, ProductField.SIZE_PRODUCT_CODE));
			}
		}
		return result;
	}

	private Product createProduct(ProductForm productForm) {
		Product product;
		if (productForm.getProductId() != null) {
			product = productService.findById(productForm.getProductId());
		} else {
			product = new Product();
		}
		product.setProductCode(productForm.getProductCode());
		product.setProductName(productForm.getProductName());
		product.setPrice(productForm.getPrice());
		product.setOrigin(productForm.getOrigin());
		product.setDetail(productForm.getDetail());
		return product;
	}

	private List<AlertDetail> checkConditionInsert(ProductForm productForm, Locale locale) {
		List<AlertDetail> result = new ArrayList<AlertDetail>();
		result = checkConditionForm(productForm, locale);
		if (checkProductCodeExist(productForm.getProductCode())) {
			result.add(AlertFactory.generateAlertDetail("product.code.exist", AlertType.ERRORS, messageSource, locale));
		}
		return result;
	}

	private List<AlertDetail> checkConditionUpdate(ProductForm productForm, Locale locale) {
		List<AlertDetail> result = new ArrayList<AlertDetail>();
		result = checkConditionForm(productForm, locale);
		boolean checkProductCodeExistWhenUpdate = productService
				.checkProductByProductCodeNeProductId(productForm.getProductCode(), productForm.getProductId());
		if (checkProductCodeExistWhenUpdate) {
			result.add(AlertFactory.generateAlertDetail("product.code.exist", AlertType.ERRORS, messageSource, locale));
		}
		return result;
	}

	private List<AlertDetail> checkConditionForm(ProductForm productForm, Locale locale) {
		List<AlertDetail> result = new ArrayList<AlertDetail>();
		if (productForm.getProductCode() == null || productForm.getProductCode().isEmpty()) {
			result.add(AlertFactory.generateAlertDetail("product.code.null", AlertType.ERRORS, messageSource, locale));
		} else if (productForm.getProductCode().length() > ProductField.SIZE_PRODUCT_CODE) {
			result.add(AlertFactory.generateAlertDetail("product.code.max.length", AlertType.ERRORS, messageSource,
					locale, ProductField.SIZE_PRODUCT_CODE));
		}

		if (productForm.getProductName() == null || productForm.getProductName().isEmpty()) {
			result.add(AlertFactory.generateAlertDetail("product.name.null", AlertType.ERRORS, messageSource, locale));
		} else if (productForm.getProductName().length() > ProductField.SIZE_PRODUCT_NAME) {
			result.add(AlertFactory.generateAlertDetail("product.name.max.length", AlertType.ERRORS, messageSource,
					locale, ProductField.SIZE_PRODUCT_NAME));
		}

		if (productForm.getOrigin() != null && productForm.getOrigin().length() > ProductField.SIZE_PRODUCT_ORIGIN) {
			result.add(AlertFactory.generateAlertDetail("product.origin.max.length", AlertType.ERRORS, messageSource,
					locale, ProductField.SIZE_PRODUCT_ORIGIN));
		}
		if (productForm.getDetail() != null && productForm.getDetail().length() > ProductField.SIZE_PRODUCT_DETAIL) {
			result.add(AlertFactory.generateAlertDetail("product.detail.max.length", AlertType.ERRORS, messageSource,
					locale, ProductField.SIZE_PRODUCT_DETAIL));
		}

		return result;
	}

	private boolean checkProductCodeExist(String productCode) {
		boolean result = false;
		if (productService.findByProductCode(productCode) != null) {
			result = true;
		}
		return result;
	}

}
