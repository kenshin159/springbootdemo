package com.nash.tech.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.family.tech.model.jpa.Stock;
import com.family.tech.repository.StockRepository;
import com.family.tech.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceImplTest {
	@Autowired
	private StockService stockService;

	@MockBean
	private StockRepository stockRepository;

	Stock stock;
	Long stockId;
	List<Stock> listStock;

	@Before
	public void setup() throws Exception {
		stock = new Stock();
		stockId = 1l;
		stock.setStockId(stockId);
		stock.setStockCode("stockCode");
		stock.setStockName("stockName");
		stock.setAddress("address");
		Mockito.when(stockRepository.findOne(stockId)).thenReturn(stock);
		Mockito.when(stockRepository.save(Mockito.any(Stock.class))).thenReturn(stock);
		listStock = new ArrayList<Stock>();
		listStock.add(stock);
		Mockito.when(stockRepository.save(listStock)).thenReturn(listStock);
		Mockito.when(stockRepository.findAll()).thenReturn(listStock);
	}

	@Test
	public void testFindAll() {
		List<Stock> result = stockService.findAll();
		Stock stock = result.get(0);
		assertEquals(stock.getStockCode(), "stockCode");
		assertEquals(stock.getStockName(), "stockName");
		assertEquals(stock.getAddress(), "address");
	}

	@Test
	public void shouldFindByIdSuccess() {
		Stock stock = stockService.findById(stockId);
		assertEquals(stock.getStockCode(), "stockCode");
		assertEquals(stock.getStockName(), "stockName");
		assertEquals(stock.getAddress(), "address");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldSaveSuccess() {
		Stock stockSave = stockService.save(stock);
		assertEquals(stockSave.getStockCode(), "stockCode");
		assertEquals(stockSave.getStockName(), "stockName");
		assertEquals(stockSave.getAddress(), "address");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldSaveListStockSuccess() {
		List<Stock> result = stockService.save(listStock);
		Stock stockSave = result.get(0);
		assertEquals(stockSave.getStockCode(), "stockCode");
		assertEquals(stockSave.getStockName(), "stockName");
		assertEquals(stockSave.getAddress(), "address");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldDeleteSuccess() {
		stockService.delete(stock);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void shouldDeleteListStockSuccess() {
		stockService.delete(listStock);
	}
}
