package com.sdase.docvalidator.util;

/**
 * @author akif
 * @since 3/10/22
 */
public enum DocValidationType {

	MONEY_LAUNDERING_CHECK("Money Laundering Check");

	private String naturalName;

	DocValidationType(String naturalName) {
		this.naturalName = naturalName;
	}

	public String getNaturalName() {
		return naturalName;
	}
}
