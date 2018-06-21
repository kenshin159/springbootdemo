package com.nash.tech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.family.tech.business.ProductBusiness;
import com.family.tech.controller.ProductController;
import com.family.tech.model.jpa.Product;
import com.family.tech.service.ProductService;
import com.family.tech.service.ProductStockService;
import com.family.tech.service.StockService;
import com.family.tech.service.external.ProductExternalService;

@RunWith(SpringRunner.class)
public class ProductControllerWebTest {

	private MockMvc mvc;

	@MockBean
	ProductService productService;

	@MockBean
	StockService stockService;

	@MockBean
	ProductStockService productStockService;

	@MockBean
	MessageSource messageSource;

	@MockBean
	ProductBusiness productBusiness;

	@MockBean
	ProductExternalService productExternalService;

	@InjectMocks
	private ProductController productController;

	List<Product> list;
	Product product;
	Long productId;

	@Before
	public void setup() throws Exception {
		productId = 1l;
		list = new ArrayList<Product>();
		product = new Product("code", "name");
		product.setProductId(productId);
		Product product1 = new Product("code1", "name1");
		list.add(product);
		list.add(product1);
		MockitoAnnotations.initMocks(this);
		// mvc = MockMvcBuilders.webAppContextSetup(context).build();
		mvc = MockMvcBuilders.standaloneSetup(productController).build();

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testLoadPageProduct() throws Exception {
		Mockito.when(productService.findAll()).thenReturn(list);

		mvc.perform(get("/products")).andExpect(status().isOk()).andExpect(view().name("product/products"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("productForm")).andReturn();

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testGetProductById() throws Exception {
		Mockito.when(productService.findById(10L)).thenReturn(product);

		mvc.perform(get("/products/{id}", 10L).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("product/product_update")).andReturn();

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testGetProductCreate() throws Exception {

		mvc.perform(get("/products/product-create")).andExpect(status().isOk())
				.andExpect(view().name("product/product_create")).andReturn();

	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testLoadWarehousing() throws Exception {
		Mockito.when(productService.findById(10L)).thenReturn(product);

		mvc.perform(get("/products/{id}/warehousing", 10L)).andExpect(status().isOk())
				.andExpect(view().name("product/warehousing")).andReturn();

	}

}
