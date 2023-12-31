package com.eokam.cpoint.application.dto;

import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Cpoint;
import com.eokam.cpoint.presentation.dto.CpointCreateRequest;
import com.eokam.cpoint.presentation.dto.MemberDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CpointDto {

	private MemberDto member;

	private Integer point;

	private Long companyId;

	private ActivityType activityType;

	public static CpointDto from(Cpoint cpoint) {
		return CpointDto
			.builder()
			.member(MemberDto.builder().memberId(cpoint.getMemberId()).build())
			.activityType(cpoint.getActivityType())
			.companyId(cpoint.getCompany().getId())
			.point(cpoint.getPoint())
			.build();
	}

	public static CpointDto of(CpointCreateRequest cpointCreateRequest, MemberDto memberDto) {
		return CpointDto
			.builder()
			.member(memberDto)
			.point(Cpoint.getCpointAmountByActivityType(cpointCreateRequest.getActivityType()))
			.activityType(cpointCreateRequest.getActivityType())
			.companyId(cpointCreateRequest.getCompanyId())
			.build();
	}

}
