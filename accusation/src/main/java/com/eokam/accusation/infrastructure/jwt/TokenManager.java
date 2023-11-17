package com.eokam.accusation.infrastructure.jwt;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenManager {

	public Claims getTokenClaims(String token) {
		String[] splitToken = token.split("\\.");
		String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
		return Jwts.parser().parseClaimsJwt(unsignedToken).getBody();
	}

	public Long getMemberId(String token) {
		Claims tokenClaims = getTokenClaims(token);
		return tokenClaims.get("memberId", Long.class);
	}
}

