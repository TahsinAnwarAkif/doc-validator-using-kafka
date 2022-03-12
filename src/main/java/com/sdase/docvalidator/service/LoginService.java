package com.sdase.docvalidator.service;

import com.sdase.docvalidator.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author akif
 * @since 3/10/22
 */
@Service
public class LoginService {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	public String getJwtToken(final String username, final String password) throws Exception {
		final Authentication authentication;

		try {
			authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Username or Password ", e);
		}

		final UserDetails ud = userDetailsService.loadUserByUsername(username);

		return JwtUtils.generateToken(ud, authentication);
	}
}
