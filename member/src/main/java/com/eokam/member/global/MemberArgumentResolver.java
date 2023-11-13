package com.eokam.member.global;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.eokam.member.application.dto.MemberDto;
import com.eokam.member.infra.dto.JwtMemberDto;
import com.eokam.member.infra.external.service.JwtTokenProvider;
import com.eokam.member.presentation.annotation.JwtUser;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(JwtMemberDto.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest)webRequest.getNativeRequest();
		String accessTokenValue = Optional.ofNullable(httpServletRequest.getCookies())
			.stream()
			.flatMap(Arrays::stream)
			.filter(cookie -> "access-token".equals(cookie.getName()))
			.findFirst()
			.map(Cookie::getValue)
			.orElseThrow(() -> new JwtException(ErrorCode.NO_JWT_TOKEN.getMessage()));

		JwtMemberDto jwtMemberDto = jwtTokenProvider.parseJwtToken(accessTokenValue);
		return jwtMemberDto;
	}
}

