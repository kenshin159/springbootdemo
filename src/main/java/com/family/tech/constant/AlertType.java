package com.family.tech.constant;

public enum AlertType {
	SUCCESS("SUCCESS"), ERRORS("ERROR"), WARNINGS("WARNING"), NOTIFICATIONS("NOTIFICATION");

	private String value;

	AlertType(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
