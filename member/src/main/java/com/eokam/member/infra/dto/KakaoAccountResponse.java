package com.eokam.member.infra.dto;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoAccountResponse {
	String nickname;

	String profileImageUrl;

	@Builder
	public KakaoAccountResponse(String nickname, String profileImageUrl) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}
}
