package com.family.tech.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.family.tech.form.StockForm;
import com.family.tech.service.StockService;

@Component
public class StockValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return StockForm.class.isAssignableFrom(clazz);
	}

	@Autowired
	StockService stockService;

	@Override
	public void validate(Object target, Errors errors) {
		StockForm stock = (StockForm) target;

		String stockCode = stock.getStockCode();
		String stockName = stock.getStockName();

		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stockCode",
		// "error.stockCode", "Please input value");
		if (checkStockCodeExist(stockCode)) {
			errors.rejectValue("stockCode", "stock.code.exist");
		}
	}

	private boolean checkStockCodeExist(String stockCode) {
		boolean result = false;
		if (stockService.findByStockCode(stockCode) != null) {
			result = true;
		}
		return result;
	}
}
