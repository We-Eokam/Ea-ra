package com.eokam.cpoint.presentation.dto;

import com.eokam.cpoint.domain.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CpointCreateRequest {

	@NotNull(message = "memberId가 필요합니다.")
	private Long memberId;

	@NotNull(message = "탄소중립실천포인트 관련 활동이 필요합니다.")
	private ActivityType activityType;

	@NotNull(message = "회사 PK가 필요합니다.")
	private Long companyId;

}
