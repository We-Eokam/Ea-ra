package com.eokam.cpoint.application.dto;

import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyPolicyDto {

	private ActivityType activityType;

	private String target;

	public static CompanyPolicyDto from(CompanyPolicy companyPolicy) {
		return CompanyPolicyDto
			.builder()
			.activityType(companyPolicy.getActivityType())
			.target(companyPolicy.getTarget())
			.build();
	}
}
