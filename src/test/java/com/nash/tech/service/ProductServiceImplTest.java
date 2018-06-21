package com.nash.tech.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.family.tech.model.jpa.Product;
import com.family.tech.repository.ProductRepository;
import com.family.tech.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
	private ProductService productService;

	@MockBean
	private ProductRepository productRepository;

	private Product product;
	private Long productId;

	@Before
	public void setup() throws Exception {
		product = new Product("productCode", "productName");
		productId = 1l;
		product.setProductId(productId);
		product.setPrice(100.9d);
		product.setOrigin("origin");
		product.setDetail("detail");
		Mockito.when(productRepository.getOne(productId)).thenReturn(product);
		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

	}

	@Test
	public void shouldFindByIdSuccess() {
		Product product = productService.findById(productId);
		assertEquals(product.getProductCode(), "productCode");
		assertEquals(product.getProductName(), "productName");
		assertEquals(product.getOrigin(), "origin");
		assertEquals(product.getDetail(), "detail");
		assertEquals(product.getPrice().toString(), "100.9");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldSaveSuccess() {
		Product productSave = productService.save(product);
		assertEquals(productSave.getProductCode(), "productCode");
		assertEquals(productSave.getProductName(), "productName");
		assertEquals(productSave.getOrigin(), "origin");
		assertEquals(productSave.getDetail(), "detail");
		assertEquals(productSave.getPrice().toString(), "100.9");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldDeleteSuccess() {
		productService.delete(product);
	}
}
