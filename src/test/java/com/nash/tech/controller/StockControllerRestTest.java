package com.nash.tech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.family.tech.controller.StockController;
import com.family.tech.model.jpa.Stock;
import com.family.tech.service.StockService;
import com.family.tech.validator.StockValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
@AutoConfigureMockMvc(secure = false)
public class StockControllerRestTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	StockService stockService;

	@MockBean
	StockValidator stockvalidator;

	@Test
	public void testInsertUser_status200() throws Exception {
		Mockito.when(stockService.save(new ArrayList<Stock>())).thenReturn(new ArrayList<Stock>());

		mvc.perform(get("/stocks/insert").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}
}
