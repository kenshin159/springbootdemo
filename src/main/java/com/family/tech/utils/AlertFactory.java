package com.family.tech.utils;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import com.family.tech.constant.AlertType;
import com.family.tech.pojo.common.Alert;
import com.family.tech.pojo.common.AlertDetail;

public class AlertFactory {
	private static final Logger logger = LoggerFactory.getLogger(AlertFactory.class);
	private static final String LOG_MESSAGE = "Can not read resource bundle";

	private AlertFactory() {
	}

	public static AlertDetail generateAlertDetail(String code, AlertType alertType, MessageSource messageSource,
			Locale locale, Object... params) {
		try {
			AlertDetail alertDetail = new AlertDetail();
			alertDetail.setAlertDetailCode(code);
			alertDetail.setAlertDetailMessage(messageSource.getMessage(code, params, locale));
			alertDetail.setAlertDetailType(alertType);
			return alertDetail;
		} catch (Exception e) {
			logger.info(LOG_MESSAGE, e);
			return null;
		}
	}

	public static Alert generateAlert(String code, AlertType alertType, MessageSource messageSource, Locale locale,
			Object... params) {
		try {
			Alert alert = new Alert();
			alert.setCode(code);
			alert.setMessage(messageSource.getMessage(code, params, locale));
			alert.setType(alertType);
			return alert;
		} catch (Exception e) {
			logger.info(LOG_MESSAGE, e);
			return null;
		}
	}
}
