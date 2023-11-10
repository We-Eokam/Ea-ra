package com.eokam.member.application.dto;

import com.eokam.member.domain.Member;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberDto {

	private Long memberId;

	private String nickname;

	private Integer groo;

	private Integer bill;

	private Integer billCount;

	private String profileImageUrl;

	private String profileImageFileName;

	public static MemberDto from(Member member){
		return MemberDto
			.builder()
			.memberId(member.getId())
			.nickname(member.getNickname())
			.groo(member.getGroo())
			.bill(member.getBill())
			.billCount(member.getBillCount())
			.profileImageUrl(member.getProfileImageUrl())
			.profileImageFileName(member.getProfileImageFileName())
			.build();
	}
}
