package com.family.tech.pojo.common;

import java.util.List;

import com.family.tech.constant.AlertType;

public class Alert {
	private String code;
	private String message;
	private AlertType type;
	private List<AlertDetail> alertDetails;

	public Alert() {
		super();
	}

	public Alert(String code, String message, AlertType type) {
		super();
		this.code = code;
		this.message = message;
		this.type = type;
	}

	public Alert(String code, String message, AlertType type, List<AlertDetail> alertDetails) {
		super();
		this.code = code;
		this.message = message;
		this.type = type;
		this.alertDetails = alertDetails;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlertType getType() {
		return type;
	}

	public void setType(AlertType type) {
		this.type = type;
	}

	public List<AlertDetail> getAlertDetails() {
		return alertDetails;
	}

	public void setAlertDetails(List<AlertDetail> alertDetails) {
		this.alertDetails = alertDetails;
	}

}
