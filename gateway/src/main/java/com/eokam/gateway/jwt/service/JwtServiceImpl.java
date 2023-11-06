package com.eokam.gateway.jwt.service;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

	@Value("${JWT_KEY}")
	private String key;

	private SecretKey generateKey() {
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public boolean checkToken(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().verifyWith(this.generateKey()).build()
				.parseSignedClaims(jwt);
			logger.debug("claims: {}", claims);
			return true;
		} catch (MalformedJwtException exception) {
			log.warn("WARN: Malformed Jwt Exception");
		} catch (ExpiredJwtException exception) {
			log.warn("WARN: Expired Jwt Exception");
		} catch (UnsupportedJwtException exception) {
			log.warn("WARN: Unsupported Jwt Exception");
		} catch (IllegalArgumentException exception) {
			log.warn("WARN: Jwt is Empty");
		}
		return false;
	}

	@Override
	public Object parseClaims(String accessToken) {
		try {
			return Jwts.parser()
				.verifyWith(this.generateKey())
				.build()
				.parse(accessToken).getPayload();
		} catch (ExpiredJwtException ex) {
			return ex.getClaims();
		}
	}

}
