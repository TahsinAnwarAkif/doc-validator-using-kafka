package com.sdase.docvalidator.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author akif
 * @since 3/10/22
 */
public enum Role implements GrantedAuthority {

	ROLE_ADMIN("ADMIN"),
	ROLE_USER("USER");

	private String naturalName;

	Role(String naturalName) {
		this.naturalName = naturalName;
	}

	public String getNaturalName() {
		return naturalName;
	}

	@Override
	public String getAuthority() {
		return name();
	}
}
