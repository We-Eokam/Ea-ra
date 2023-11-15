package com.eokam.member.presentation.dto;

import com.eokam.member.application.dto.MemberDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberProfileResponse {

	private Long memberId;

	private Integer groo;

	private Integer repayGroo;

	private String profileImageUrl;

	private String nickname;

	public static MemberProfileResponse from(MemberDto memberDto){
		return MemberProfileResponse
			.builder()
			.memberId(memberDto.getMemberId())
			.groo(memberDto.getGroo()-memberDto.getRepayGroo())
			.profileImageUrl(memberDto.getProfileImageUrl())
			.nickname(memberDto.getNickname())
			.repayGroo(memberDto.getRepayGroo())
			.build();
	}

}
