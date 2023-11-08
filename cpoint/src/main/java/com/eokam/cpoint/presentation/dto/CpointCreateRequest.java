package com.eokam.cpoint.presentation.dto;

import com.eokam.cpoint.domain.ActivityType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpointCreateRequest {

	@NotNull(message = "memberId가 필요합니다.")
	private Long memberId;

	@NotNull(message = "적립할 포인트가 필요합니다.")
	@Positive(message = "적립할 포인트는 양수여야합니다.")
	private Integer amount;

	@NotNull(message = "탄소중립실천포인트 관련 활동이 필요합니다.")
	private ActivityType activityType;

	@NotNull(message = "회사 PK가 필요합니다.")
	private Long companyId;

}
