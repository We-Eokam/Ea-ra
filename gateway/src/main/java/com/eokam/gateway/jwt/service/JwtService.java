package com.eokam.gateway.jwt.service;

public interface JwtService {
	boolean checkToken(String jwt);

	Object parseClaims(String accessToken);
}
