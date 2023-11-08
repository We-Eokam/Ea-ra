package com.eokam.cpoint.presentation.dto;

import com.eokam.cpoint.application.dto.CpointDto;
import com.eokam.cpoint.domain.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CpointCreateResponse {

	private Long memberId;

	private Integer point;

	private Long companyId;

	private ActivityType activityType;

	public static CpointCreateResponse from(CpointDto cpointDto) {
		return CpointCreateResponse
			.builder()
			.memberId(cpointDto.getMember().getMemberId())
			.activityType(cpointDto.getActivityType())
			.companyId(cpointDto.getCompanyId())
			.point(cpointDto.getPoint())
			.build();
	}
}
