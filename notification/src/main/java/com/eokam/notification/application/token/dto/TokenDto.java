package com.eokam.notification.application.token.dto;

import com.eokam.notification.domain.token.entity.Token;
import com.eokam.notification.infrastructure.util.ParseJwtUtil;
import com.eokam.notification.presentation.dto.TokenRequest;

import lombok.Builder;

@Builder
public record TokenDto(Long memberId, String token) {

	public static TokenDto of(String jwt, TokenRequest tokenRequest) {
		return TokenDto.builder()
			.memberId(ParseJwtUtil.parseMemberId(jwt))
			.token(tokenRequest.token())
			.build();
	}

	public static TokenDto from(Token token) {
		return TokenDto.builder()
			.memberId(Long.parseLong(token.memberId()))
			.token(token.token())
			.build();
	}
}
