package com.nash.tech.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.family.tech.business.ProductBusiness;
import com.family.tech.controller.ProductController;
import com.family.tech.form.ProductForm;
import com.family.tech.model.jpa.Product;
import com.family.tech.pojo.common.AlertDetail;
import com.family.tech.pojo.common.Page;
import com.family.tech.service.ProductService;
import com.family.tech.service.ProductStockService;
import com.family.tech.service.StockService;
import com.family.tech.service.external.ProductExternalService;
import com.family.tech.utils.ObjectMapperFactory;
import com.fasterxml.jackson.core.type.TypeReference;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerRestTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ProductService productService;

	@MockBean
	private StockService stockService;

	@MockBean
	private ProductStockService productStockService;

	@MockBean
	MessageSource messageSource;

	@MockBean
	ProductBusiness productBusiness;

	@MockBean
	ProductExternalService productExternalService;

	Long productId;
	List<AlertDetail> listAlertDetailError;
	List<Product> list;
	Locale locale;

	@Before
	public void setup() throws Exception {
		loadDataAlertDetailError();
		productId = 1l;
		list = new ArrayList<Product>();
		Product product = new Product("code", "name");
		product.setProductId(productId);
		Product product1 = new Product("code1", "name1");
		list.add(product);
		list.add(product1);

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void getAllProduct_thenStaus200() throws Exception {

		Mockito.when(productService.findAll()).thenReturn(list);

		mvc.perform(get("/products/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].productCode", is("code")));

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void updateProduct_success() throws Exception {
		ProductForm productForm = new ProductForm();
		productForm.setProductCode("Code");
		productForm.setProductName("name");
		productForm.setOrigin("origin");
		productForm.setFromPrice("10");
		productForm.setToPrice("20");
		productForm.setDetail("detail");
		Mockito.when(productBusiness.updateProduct(Matchers.any(ProductForm.class), Matchers.any(Locale.class)))
				.thenReturn(new ArrayList<AlertDetail>());

		mvc.perform(MockMvcRequestBuilders.put("/products/action-product-update")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is("update.success")));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void updateProduct_fail() throws Exception {

		Mockito.when(productBusiness.updateProduct(Matchers.any(ProductForm.class), Matchers.any(Locale.class)))
				.thenReturn(listAlertDetailError);

		mvc.perform(MockMvcRequestBuilders.put("/products/action-product-update")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code", is("update.failure")));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void createProduct_success() throws Exception {
		Mockito.when(productBusiness.insertProduct(Matchers.any(ProductForm.class), Matchers.any(Locale.class)))
				.thenReturn(new ArrayList<AlertDetail>());

		mvc.perform(MockMvcRequestBuilders.post("/products/action-product-create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", is("create.success")));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void createProduct_fail() throws Exception {
		Mockito.when(productBusiness.insertProduct(Matchers.any(ProductForm.class), Matchers.any(Locale.class)))
				.thenReturn(listAlertDetailError);

		mvc.perform(MockMvcRequestBuilders.post("/products/action-product-create")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code", is("create.failure")));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void deleteProduct() throws Exception {
		Mockito.when(productService.findById(Mockito.any(Long.class))).thenReturn(new Product());
		Mockito.doNothing().when(productService).delete(Mockito.any(Product.class));

		mvc.perform(MockMvcRequestBuilders.delete("/products/{id}", productId)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code", is("delete.success")));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testGetTotalProductByProductCode() throws Exception {
		Mockito.when(productExternalService.getTotalProductByProductCode(Mockito.any(String.class))).thenReturn(10);

		mvc.perform(MockMvcRequestBuilders.post("/products/checkStockAviable")).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testActionWarehousing_success() throws Exception {
		Mockito.when(productBusiness.onWarehousing(Matchers.any(ProductForm.class), Matchers.any(Locale.class)))
				.thenReturn(new ArrayList<AlertDetail>());

		mvc.perform(MockMvcRequestBuilders.post("/products/{id}/action-warehousing", 1l)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code", is("warehousing.success")));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testActionWarehousing_fail() throws Exception {
		Mockito.when(productBusiness.onWarehousing(Matchers.any(ProductForm.class), Matchers.any(Locale.class)))
				.thenReturn(listAlertDetailError);

		mvc.perform(MockMvcRequestBuilders.post("/products/{id}/action-warehousing", 1l)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code", is("warehousing.failure")));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testSearchFunction() throws Exception {
		ProductForm productForm = new ProductForm();
		productForm.setProductCode("Code");
		productForm.setProductName("name");
		productForm.setFromPrice("10");
		productForm.setToPrice("20");

		Page page = new Page();
		page.setStart(10);
		page.setLength(5);
		MultiValueMap<String, String> params = convertProductFormToMultiValueMap(productForm);

		Mockito.when(productService.searchProduct(Matchers.any(Map.class))).thenReturn(list);
		Mockito.when(productService.countSearchProduct(Matchers.any(Map.class))).thenReturn(10l);

		mvc.perform(MockMvcRequestBuilders.post("/products/search").param("length", Integer.toString(page.getLength()))
				.param("start", Integer.toString(page.getStart())).params(params)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data[0].productCode", is("code")));
	}

	private void loadDataAlertDetailError() {
		listAlertDetailError = new ArrayList<AlertDetail>();
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("product-controller/alert-detail-error.txt").getFile());
		try {
			listAlertDetailError = ObjectMapperFactory.getMapper().readValue(file,
					new TypeReference<List<AlertDetail>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MultiValueMap<String, String> convertProductFormToMultiValueMap(ProductForm productForm) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		Map<String, String> map = null;
		try {
			map = ObjectMapperFactory.getMapper().convertValue(productForm, new TypeReference<Map<String, String>>() {
			});
			System.out.println("MAP: " + map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> list;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			list = new ArrayList<String>();
			list.add(entry.getValue());
			params.put(entry.getKey(), list);
		}

		return params;
	}

	// public void readFile() {
	// ClassLoader classLoader = this.getClass().getClassLoader();
	// File file = new File(classLoader.getResource("abc.txt").getFile());
	// String content = "";
	// try {
	// content = new String(Files.readAllBytes(file.toPath()));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static void main(String[] args) {
	// List<AlertDetail> list = new ArrayList<AlertDetail>();
	// list.add(new AlertDetail("product.code.null", "Product Code is empty",
	// AlertType.ERRORS));
	// list.add(new AlertDetail("product.name.null", "Product Name is empty",
	// AlertType.ERRORS));
	//
	// try {
	// String abc = ObjectMapperFactory.getMapper().writeValueAsString(list);
	// System.out.println(abc);
	// } catch (JsonProcessingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
