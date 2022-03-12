package com.sdase.docvalidator.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author akif
 * @since 3/10/22
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@PersistenceContext
	private EntityManager em;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		return em.createQuery("FROM User u WHERE u.username = :username", UserDetails.class)
			.setParameter("username", username)
			.getSingleResult();
	}
}
