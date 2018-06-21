package com.nash.tech.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.family.tech.business.ProductBusiness;
import com.family.tech.form.ProductForm;
import com.family.tech.model.jpa.Product;
import com.family.tech.model.jpa.ProductStock;
import com.family.tech.model.jpa.Stock;
import com.family.tech.pojo.common.AlertDetail;
import com.family.tech.service.ProductService;
import com.family.tech.service.ProductStockService;
import com.family.tech.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductBusinessTest {

	@Autowired
	ProductBusiness productBusiness;

	@MockBean
	ProductService productService;

	@MockBean
	StockService stockService;

	@MockBean
	ProductStockService productStockService;

	@TestConfiguration
	static class ProductServiceImplTestContextConfiguration {
		@Bean
		public ProductBusiness productBusiness() {
			return new ProductBusiness();
		}
	}

	private Product product;
	private Long productId;
	private ProductForm productForm;
	private Locale locale;
	private Stock stock;
	List<ProductStock> list;

	@Before
	public void setup() throws Exception {
		locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
		product = new Product("code", "productName");
		productId = 1l;
		product.setProductId(productId);
		product.setPrice(100.9d);
		product.setOrigin("origin");
		product.setDetail("detail");

		stock = new Stock();

		Mockito.when(productService.findById(productId)).thenReturn(product);
		Mockito.when(productService.save(Mockito.any(Product.class))).thenReturn(product);
		Mockito.when(productService.findByProductCode("code")).thenReturn(product);
		Mockito.when(productService.checkProductByProductCodeNeProductId("code", productId)).thenReturn(true);

		//
		list = new ArrayList<ProductStock>();
		list.add(new ProductStock());
		Mockito.when(productStockService.save(Mockito.any(ProductStock.class))).thenReturn(new ProductStock());
		Mockito.when(stockService.findById(Mockito.any(Long.class))).thenReturn(stock);

	}

	@Test
	public void shouldInsertProductFailWhenFieldNull() {
		productForm = new ProductForm();
		List<AlertDetail> listAlertDetail = productBusiness.insertProduct(productForm, locale);
		assertEquals(listAlertDetail.size(), 2);
		assertEquals(listAlertDetail.get(0).getAlertDetailMessage(), "Product Code is empty");
		assertEquals(listAlertDetail.get(0).getAlertDetailCode(), "product.code.null");
		assertEquals(listAlertDetail.get(0).getAlertDetailType().getValue(), "ERROR");
	}

	@Test
	public void shouldInsertProductFailWhenFieldOverMaxLength() {
		productForm = new ProductForm();
		productForm.setProductCode("123456789");
		productForm.setProductName("1234567890a1234567890a1234567890a1234567890a1234567890");
		productForm.setDetail(
				"text_have_max_length_over_256_character(text_have_max_length_over_256_charactertext_have_max_length_over_256_charactertext_have_max_length_over_256_charactertext_have_max_length_over_256_charactertext_have_max_length_over_256_charactertext_have_max_length_)");
		productForm.setOrigin("text_have_max_length_over_50_character(text_have_m)");
		List<AlertDetail> listAlertDetail = productBusiness.insertProduct(productForm, locale);
		assertEquals(listAlertDetail.size(), 4);
	}

	@Test
	public void shouldInsertProductFailWhenProductExist() {
		productForm = new ProductForm();
		productForm.setProductCode("code");
		productForm.setProductName("productName");
		List<AlertDetail> listAlertDetail = productBusiness.insertProduct(productForm, locale);
		assertEquals(listAlertDetail.size(), 1);
		assertEquals(listAlertDetail.get(0).getAlertDetailMessage(), "Product Code existed");
	}

	@Test
	public void shouldInsertProductSuccess() {
		productForm = new ProductForm();
		productForm.setProductCode("12345");
		productForm.setProductName("productName");
		List<AlertDetail> listAlertDetail = productBusiness.insertProduct(productForm, locale);
		assertThat(listAlertDetail, IsEmptyCollection.empty());
	}

	@Test
	public void shouldUpdateProductFailWhenProductExist() {
		productForm = new ProductForm();
		productForm.setProductCode("code");
		productForm.setProductName("productName");
		productForm.setProductId(productId);
		List<AlertDetail> listAlertDetail = productBusiness.updateProduct(productForm, locale);
		assertEquals(listAlertDetail.size(), 1);
		assertEquals(listAlertDetail.get(0).getAlertDetailMessage(), "Product Code existed");
	}

	@Test
	public void shouldUpdateProductSuccess() {
		productForm = new ProductForm();
		productForm.setProductCode("12345");
		productForm.setProductName("productName");
		List<AlertDetail> listAlertDetail = productBusiness.updateProduct(productForm, locale);
		assertThat(listAlertDetail, IsEmptyCollection.empty());
	}

	@Test
	public void shouldOnWarehousingSuccess() {
		productForm = new ProductForm();
		productForm.setProductCode("12345");
		productForm.setProductName("productName");
		productForm.setNumber("12");
		List<AlertDetail> listAlertDetail = productBusiness.onWarehousing(productForm, locale);
		assertThat(listAlertDetail, IsEmptyCollection.empty());
	}

	@Test
	public void shouldOnWarehousingSuccess1() {
		Mockito.when(productStockService.findByProductIdAndStockId(Mockito.any(Long.class), Mockito.any(Long.class)))
				.thenReturn(list);
		productForm = new ProductForm();
		productForm.setProductCode("12345");
		productForm.setProductName("productName");
		productForm.setNumber("12");
		List<AlertDetail> listAlertDetail = productBusiness.onWarehousing(productForm, locale);
		assertThat(listAlertDetail, IsEmptyCollection.empty());
	}

	@Test
	public void shouldOnWarehousingFailWithNumberWrongFormat() {
		productForm = new ProductForm();
		productForm.setProductCode("12345");
		productForm.setProductName("productName");
		productForm.setNumber("12a");
		List<AlertDetail> listAlertDetail = productBusiness.onWarehousing(productForm, locale);
		assertEquals(listAlertDetail.size(), 1);
		assertEquals(listAlertDetail.get(0).getAlertDetailMessage(), "Number must be integer");
	}

	@Test
	public void shouldOnWarehousingFailWithNumberNull() {
		productForm = new ProductForm();
		productForm.setProductCode("12345");
		productForm.setProductName("productName");
		productForm.setNumber("");
		List<AlertDetail> listAlertDetail = productBusiness.onWarehousing(productForm, locale);
		assertEquals(listAlertDetail.size(), 1);
		assertEquals(listAlertDetail.get(0).getAlertDetailMessage(), "Number is empty");
	}
}
