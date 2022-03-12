package com.sdase.docvalidator.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author akif
 * @since 3/10/22
 */
public class JwtUtils {

	private static final String SECRET_KEY = "secret";

	public static String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public static Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);

		return claimsResolver.apply(claims);
	}

	public static String generateToken(UserDetails userDetails, Authentication authentication) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("principal", authentication.getPrincipal());

		return createToken(claims, userDetails.getUsername());
	}

	public static Boolean validateToken(String token) {
		final String username = extractUsername(token);

		return (username.equals(extractUsername(token)) && !isTokenExpired(token));
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getAuthorityList(String token) {
		return (List<Map<String, String>>)
			((Map<String, Object>) extractAllClaims(token).get("principal")).get("authorities");
	}

	private static String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(username)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}

	private static Claims extractAllClaims(String token) {
		return Jwts.parser()
			.setSigningKey(SECRET_KEY)
			.parseClaimsJws(token)
			.getBody();
	}

	private static Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
