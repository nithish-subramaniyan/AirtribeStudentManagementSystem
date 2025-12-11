package com.airtribe.studentmanagement.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

	private String secret = "bYe4yBhmjJKWAUOlHqfUl+JgPy2LHhGJOgSHtnWxR2E=";

	private long expirationMs = 3600000;

	private Key signingKey;

	@PostConstruct
	public void init() {
		try {
			if (secret == null || secret.isBlank()) {
				throw new IllegalStateException(
						"JWT secret is missing. Add 'app.jwt.secret' to application.properties (must be >= 32 bytes).");
			}

			byte[] keyBytes;
			try {
				byte[] decoded = Base64.getDecoder().decode(secret);
				log.debug("Decoded base64 jwt secret length = {}", decoded.length);
				// use decoded only if looks long enough
				if (decoded.length >= 32) {
					keyBytes = decoded;
				} else {
					keyBytes = secret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
					log.debug("Base64 decode yielded {} bytes (<32), using raw UTF-8 bytes length {}", decoded.length,
							keyBytes.length);
				}
			} catch (IllegalArgumentException ex) {
				// not valid base64 - use raw bytes
				keyBytes = secret.getBytes(java.nio.charset.StandardCharsets.UTF_8);
				log.debug("Secret is not valid base64; using raw UTF-8 bytes length {}", keyBytes.length);
			}

			if (keyBytes.length < 32) {
				String msg = "JWT secret is too short (" + keyBytes.length
						+ " bytes). Use at least 32 bytes (e.g. a 256-bit key). Generate one with: openssl rand -base64 32";
				log.error(msg);
				throw new IllegalStateException(msg);
			}

			// create signing key (may throw WeakKeyException if invalid)
			signingKey = Keys.hmacShaKeyFor(keyBytes);
			log.info("JwtUtil initialized successfully (key length {}).", keyBytes.length);
		} catch (Exception ex) {
			log.error("JwtUtil initialization failed: {}", ex.toString(), ex);
			// rethrow so Spring fails fast but with logged cause
			throw ex;
		}
	}

	public String generateToken(String username) {
		Date now = new Date();
		Date exp = new Date(now.getTime() + expirationMs);
		return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(exp)
				.signWith(signingKey, SignatureAlgorithm.HS256).compact();
	}

	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			log.debug("Token validation failed: {}", ex.getMessage());
			return false;
		}
	}

	public String extractUsername(String token) {
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
	}
}
