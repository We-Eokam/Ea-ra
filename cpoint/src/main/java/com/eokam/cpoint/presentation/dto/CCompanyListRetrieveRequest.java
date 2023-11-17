package com.eokam.cpoint.presentation.dto;

import com.eokam.cpoint.domain.ActivityType;

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
public class CCompanyListRetrieveRequest {

	@NotNull(message = "활동 타입이 필요합니다.")
	ActivityType activityType;

}
