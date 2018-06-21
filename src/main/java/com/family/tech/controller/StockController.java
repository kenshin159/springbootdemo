package com.family.tech.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.family.tech.convertor.StockConvertor;
import com.family.tech.form.StockForm;
import com.family.tech.model.jpa.Stock;
import com.family.tech.service.ProductService;
import com.family.tech.service.StockService;
import com.family.tech.validator.StockValidator;

@Controller
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private ProductService productService;

	@Autowired
	private StockValidator stockValidator;

	@GetMapping("/insert")
	@ResponseBody
	public List<Stock> stockInsert() {
		System.out.println("INSERT STOCK");
		System.out.println("productService of StockController: " + productService);
		List<Stock> listStock = new ArrayList<Stock>();
		listStock.add(new Stock("STO001", "STOCK NUMBER 1"));
		listStock.add(new Stock("STO002", "STOCK NUMBER 2"));
		listStock.add(new Stock("STO003", "STOCK NUMBER 3"));

		List<Stock> stocksSave = stockService.save(listStock);
		System.out.println("stocksSave: " + stocksSave);
		return stockService.findAll();
	}

	// @InitBinder
	// public void initBinder(WebDataBinder webDataBinder) {
	// webDataBinder.setValidator(stockValidator);
	// }

	@GetMapping("/stock-create")
	public String showFormStockCreate(StockForm stockForm) {
		System.out.println("abc");
		return "stock/stock_create";
	}

	@PostMapping("/stock-create")
	public String checkPersonInfo(@Valid StockForm stockForm, BindingResult bindingResult, Model model) {

		stockValidator.validate(stockForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "stock/stock_create";
			// return "redirect:/home";
			// return "forward:/products/action-product-create";
		}
		Stock stock = StockConvertor.convertStockFormToStock(stockForm);
		stockService.save(stock);
		model.addAttribute("message", "Create successfull");
		return "stock/stock_create";
	}
}
