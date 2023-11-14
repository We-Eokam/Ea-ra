package com.eokam.gateway.util;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class CookieUtil {
	public Optional<String> getCookie(ServerHttpRequest request, String cookieName) {
		final MultiValueMap<String, HttpCookie> cookies = request.getCookies();

		System.out.println(cookies);

		if (cookies.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(Objects.requireNonNull(cookies.getFirst(cookieName)).getValue());
	}
}
