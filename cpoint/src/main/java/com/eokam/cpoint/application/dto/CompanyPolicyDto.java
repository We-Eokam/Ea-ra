package com.eokam.cpoint.application.dto;

import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.CompanyPolicy;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
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
