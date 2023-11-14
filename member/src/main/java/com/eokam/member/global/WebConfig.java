package com.eokam.member.global;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final MemberArgumentResolver memberArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(memberArgumentResolver);
	}

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins("http://localhost:5371","https://www.ea-ra.com","http://localhost:5000")
			.allowedMethods("GET", "POST","PUT","DELETE")
			.allowCredentials(true)
			.maxAge(3000);
	}
}
