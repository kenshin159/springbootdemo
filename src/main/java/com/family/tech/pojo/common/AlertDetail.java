package com.family.tech.pojo.common;

import com.family.tech.constant.AlertType;

public class AlertDetail {
	private String alertDetailCode;
	private String alertDetailMessage;
	private AlertType alertDetailType;

	public AlertDetail() {
		super();
	}

	public AlertDetail(String alertDetailCode, String alertDetailMessage, AlertType alertDetailType) {
		super();
		this.alertDetailCode = alertDetailCode;
		this.alertDetailMessage = alertDetailMessage;
		this.alertDetailType = alertDetailType;
	}

	public String getAlertDetailCode() {
		return alertDetailCode;
	}

	public void setAlertDetailCode(String alertDetailCode) {
		this.alertDetailCode = alertDetailCode;
	}

	public String getAlertDetailMessage() {
		return alertDetailMessage;
	}

	public void setAlertDetailMessage(String alertDetailMessage) {
		this.alertDetailMessage = alertDetailMessage;
	}

	public AlertType getAlertDetailType() {
		return alertDetailType;
	}

	public void setAlertDetailType(AlertType alertDetailType) {
		this.alertDetailType = alertDetailType;
	}

}
