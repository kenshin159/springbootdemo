package com.family.tech.constant.security;

public enum RoleType {
	ROLE_ADMIN("ROLE_ADMIN"), ROLE_REGULAR_USER("ROLE_REGULAR_USER");

	private String value;

	RoleType(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
