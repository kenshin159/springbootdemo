package com.family.tech.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.family.tech.business.ProductBusiness;
import com.family.tech.constant.AlertType;
import com.family.tech.constant.PageField;
import com.family.tech.constant.ProductField;
import com.family.tech.constant.security.RoleType;
import com.family.tech.convertor.ProductConvertor;
import com.family.tech.form.ProductForm;
import com.family.tech.model.jpa.Product;
import com.family.tech.pojo.common.Alert;
import com.family.tech.pojo.common.AlertDetail;
import com.family.tech.pojo.common.DataPagination;
import com.family.tech.pojo.common.Page;
import com.family.tech.service.ProductService;
import com.family.tech.service.StockService;
import com.family.tech.service.external.ProductExternalService;
import com.family.tech.utils.AlertFactory;

@Controller
@RequestMapping("/products")
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private StockService stockService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductBusiness productBusiness;

	@Autowired
	private ProductExternalService productExternalService;

	private boolean chechRole(String role) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role);
			if (hasRole) {
				return true;
			}
		}
		return false;
	}

	@GetMapping()
	public String loadPageProduct(Model model) {
		model.addAttribute("productForm", new ProductForm());
		model.addAttribute("authorizeShowButton", chechRole(RoleType.ROLE_ADMIN.getValue()));
		logger.info("Returning products:");
		return "product/products";
	}

	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Product> getAllProduct() {
		logger.info("ABC");
		return productService.findAll();
	}

	@GetMapping(value = "/{id}")
	public String getProduct(@PathVariable("id") Long id, Model model) {
		logger.info("Redirect update product");
		model.addAttribute("productForm", productService.findById(id));
		model.addAttribute("authorizeShowButton", chechRole(RoleType.ROLE_ADMIN.getValue()));
		return "product/product_update";
	}

	@PutMapping("/action-product-update")
	@ResponseBody
	public Alert updateProduct(ProductForm productForm, Locale locale) {
		Alert alert = new Alert();
		List<AlertDetail> alertDetails = productBusiness.updateProduct(productForm, locale);
		if (alertDetails.isEmpty()) {
			alert = AlertFactory.generateAlert("update.success", AlertType.SUCCESS, messageSource, locale);
		} else {
			alert = AlertFactory.generateAlert("update.failure", AlertType.ERRORS, messageSource, locale);
			alert.setAlertDetails(alertDetails);
		}
		return alert;
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/product-create")
	public String getProductCreate(Model model) {
		logger.info("product-create");
		model.addAttribute("productForm", new ProductForm());
		return "product/product_create";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/action-product-create")
	@ResponseBody
	public Alert createProduct(ProductForm productForm, Locale locale) {
		Alert alert = new Alert();
		List<AlertDetail> alertDetails = productBusiness.insertProduct(productForm, locale);
		if (alertDetails.isEmpty()) {
			alert = AlertFactory.generateAlert("create.success", AlertType.SUCCESS, messageSource, locale);
		} else {
			alert = AlertFactory.generateAlert("create.failure", AlertType.ERRORS, messageSource, locale);
			alert.setAlertDetails(alertDetails);
		}
		return alert;
	}

	@DeleteMapping(value = "/{id}")
	@ResponseBody
	public Alert deleteProduct(@PathVariable("id") String id, Locale locale) {
		logger.info("Delete product");
		Long productId = Long.parseLong(id);
		Product product = productService.findById(productId);
		productService.delete(product);
		Alert alert = new Alert();
		alert = AlertFactory.generateAlert("delete.success", AlertType.SUCCESS, messageSource, locale);
		return alert;
	}

	@GetMapping(value = "/{id}/warehousing")
	public String loadWarehousing(@PathVariable("id") Long id, Model model) {
		logger.info("Redirect update product");
		model.addAttribute("productForm", ProductConvertor.convertProductToProductForm(productService.findById(id)));
		model.addAttribute("stocks", stockService.findAll());
		return "product/warehousing";
	}

	@PostMapping(value = "/{id}/action-warehousing")
	@ResponseBody
	public Alert actionWarehousing(@PathVariable("id") Long id, ProductForm productForm, Locale locale) {
		logger.info("action warehousing");
		logger.info(productForm.getNumber() + ", " + productForm.getStockId());
		Alert alert = new Alert();
		List<AlertDetail> alertDetails = productBusiness.onWarehousing(productForm, locale);
		if (alertDetails.isEmpty()) {
			alert = AlertFactory.generateAlert("warehousing.success", AlertType.SUCCESS, messageSource, locale);
		} else {
			alert = AlertFactory.generateAlert("warehousing.failure", AlertType.ERRORS, messageSource, locale);
			alert.setAlertDetails(alertDetails);
		}
		return alert;
	}

	@PostMapping("/checkStockAviable")
	@ResponseBody
	public int getTotalProductByProductCode(String productCode) {
		logger.info("Check productCode: " + productCode);
		return productExternalService.getTotalProductByProductCode(productCode);
	}

	@PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DataPagination<List<Product>> search(ProductForm productForm, Page page) {
		Map<String, Object> mapCondition = new HashMap<String, Object>();
		if (productForm != null) {
			if (productForm.getProductCode() != null && !"".equals(productForm.getProductCode().trim())) {
				mapCondition.put(ProductField.PRODUCT_CODE, productForm.getProductCode());
			}
			if (productForm.getProductName() != null && !"".equals(productForm.getProductName().trim())) {
				mapCondition.put(ProductField.PRODUCT_NAME, productForm.getProductName());
			}

			if (productForm.getFromPrice() != null && !"".equals(productForm.getFromPrice().trim())) {
				mapCondition.put(ProductField.FROM_PRICE, Double.parseDouble(productForm.getFromPrice()));
			}
			if (productForm.getToPrice() != null && !"".equals(productForm.getToPrice().trim())) {
				mapCondition.put(ProductField.TO_PRICE, Double.parseDouble(productForm.getToPrice()));
			}
			if (productForm.getOrigin() != null && !"".equals(productForm.getOrigin().trim())) {
				mapCondition.put(ProductField.ORIGIN, productForm.getOrigin());
			}
		}
		if (page != null) {
			if (page.getStart() > 0) {
				mapCondition.put(PageField.START_ROW, page.getStart());
			}
			if (page.getLength() > 0) {
				mapCondition.put(PageField.PAGE_SIZE, page.getLength());
			}
		}
		List<Product> list = productService.searchProduct(mapCondition);
		DataPagination<List<Product>> dataPagination = new DataPagination<>();
		if (list == null || list.isEmpty()) {
			dataPagination.setData(new ArrayList<Product>());
		} else {
			long total = productService.countSearchProduct(mapCondition);
			dataPagination.setData(list);
			dataPagination.setRecordsTotal(total);
			dataPagination.setRecordsFiltered(total);
		}
		return dataPagination;
	}

}
