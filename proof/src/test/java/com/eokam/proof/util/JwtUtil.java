package com.eokam.proof.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

	public static String createAccessToken(Long memberId) {
		String role = "member";
		return createToken("access-token", memberId, role,
			new Date(System.currentTimeMillis() + Long.parseLong("6000000")));
	}

	public static String createToken(String tokenType, Long memberId, String role, Date expirationTime) {
		String tokenSecret = "tjdnf5qksdjzkaxladmldjfkvmfhwprxmfpcmrh";
		return Jwts.builder()
			.setSubject(tokenType)
			.setIssuedAt(new Date())
			.setExpiration(expirationTime)
			.claim("role", role)
			.claim("memberId", memberId)
			.signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
			.setHeaderParam("type", "JWT")
			.compact();
	}

}
