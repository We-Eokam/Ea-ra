package com.eokam.member.presentation.dto;

import com.eokam.member.application.dto.MemberDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberDetailResponse {

	private Long memberId;

	private String nickname;

	private Integer groo;

	private Integer bill;

	private Integer billCount;

	private String profileImageUrl;

	private Boolean isTestDone;

	public static MemberDetailResponse from(MemberDto memberDto){
		return MemberDetailResponse
			.builder()
			.memberId(memberDto.getMemberId())
			.nickname(memberDto.getNickname())
			.groo(memberDto.getGroo())
			.bill(memberDto.getBill())
			.billCount(memberDto.getBillCount())
			.profileImageUrl(memberDto.getProfileImageUrl())
			.isTestDone(memberDto.getIsTestDone())
			.build();
	}

}
