package com.sdase.docvalidator.util;

/**
 * @author akif
 * @since 3/11/22
 */
public enum ValidationType {
	MONEY_LAUNDERING("Money Laundering Check");

	private String naturalName;

	ValidationType(String naturalName) {
		this.naturalName = naturalName;
	}

	public String getNaturalName() {
		return naturalName;
	}
}
