package com.eokam.gateway.filter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.eokam.gateway.jwt.service.JwtService;
import com.eokam.gateway.util.CookieUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config> {
	private JwtService jwtService;
	private CookieUtil cookieUtil;

	private static final String ACCESS_TOKEN = "access-token";

	public CustomAuthFilter() {
		super(Config.class);
	}

	@Autowired
	public void setJwtService(JwtService jwtService, CookieUtil cookieUtil) {
		this.jwtService = jwtService;
		this.cookieUtil = cookieUtil;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			Optional<String> optionalAccessToken = cookieUtil.getCookie(request, ACCESS_TOKEN);

			log.info("[Request Path] {}", request.getPath());

			if (optionalAccessToken.isEmpty()) {
				log.warn("Access Token Not Exist");
				return handleUnAuthorized(exchange);
			}

			String accessToken = optionalAccessToken.get();

			if (!jwtService.checkToken(accessToken)) {
				return handleUnAuthorized(exchange);
			}

			return chain.filter(exchange.mutate().request(request).build());
		}));
	}

	private Mono<Void> handleUnAuthorized(ServerWebExchange exchange) {
		ServerHttpResponse response = exchange.getResponse();

		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		return response.setComplete();
	}

	public static class Config {
	}
}
