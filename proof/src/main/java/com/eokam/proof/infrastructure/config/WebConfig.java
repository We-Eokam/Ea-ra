package com.eokam.proof.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins("http://localhost:5371", "https://www.ea-ra.com", "http://www.ea-ra.com",
				"https://dev.ea-ra.com", "http://dev.ea-ra.com", "dev.ea-ra.com",
				"www.ea-ra.com", "ea-ra.com", "http://localhost:5000")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowCredentials(true)
			.maxAge(3000);
	}
}
