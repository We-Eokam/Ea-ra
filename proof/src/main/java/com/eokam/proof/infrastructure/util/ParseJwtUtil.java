package com.eokam.proof.infrastructure.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ParseJwtUtil {

	public static Claims getTokenClaims(String token) {
		String[] splitToken = token.split("\\.");
		String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";
		return Jwts.parser().parseClaimsJwt(unsignedToken).getBody();
	}

	public static Long parseMemberId(String token) {
		Claims tokenClaims = getTokenClaims(token);
		return tokenClaims.get("memberId", Long.class);
	}

}
