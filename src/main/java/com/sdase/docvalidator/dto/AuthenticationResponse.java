package com.sdase.docvalidator.dto;

import java.io.Serializable;

/**
 * @author akif
 * @since 3/10/22
 */
public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String jwt;

	public AuthenticationResponse() {
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
