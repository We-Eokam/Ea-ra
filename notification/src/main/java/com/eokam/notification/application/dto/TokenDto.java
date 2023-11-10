package com.eokam.notification.application.dto;

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

}
