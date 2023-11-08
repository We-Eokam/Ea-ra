package com.eokam.cpoint.global;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.eokam.cpoint.global.exception.JwtException;
import com.eokam.cpoint.presentation.dto.MemberDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(MemberDto.class);
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
			.orElseThrow(() -> new JwtException(ErrorCode.JWT_NOT_FOUND));

		MemberDto memberDto = JwtUtil.parseMember(accessTokenValue);
		return memberDto;
	}
}
