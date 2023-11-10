package com.eokam.notification.domain.entity;

import com.eokam.notification.application.dto.TokenDto;

import lombok.Builder;

@Builder
public record Token(String memberId, String token) {
	public static Token from(TokenDto tokenDto) {
		return Token.builder()
			.memberId(String.valueOf(tokenDto.memberId()))
			.token(tokenDto.token())
			.build();
	}

	public static Token of(Long memberId, String token) {
		return Token.builder()
			.memberId(String.valueOf(memberId))
			.token(token)
			.build();
	}
}
