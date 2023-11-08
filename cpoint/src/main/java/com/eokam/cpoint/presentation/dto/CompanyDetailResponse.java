package com.eokam.cpoint.presentation.dto;

import java.util.List;

import com.eokam.cpoint.application.dto.CompanyDetailDto;
import com.eokam.cpoint.application.dto.CompanyDto;
import com.eokam.cpoint.application.dto.CompanyPolicyDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyDetailResponse {

	private Long id;

	private String name;

	private Boolean isConnect;

	private List<CompanyPolicyDto> policies;

	public static CompanyDetailResponse from(CompanyDetailDto companyDetailDto) {
		return CompanyDetailResponse
			.builder()
			.id(companyDetailDto.getCompanyId())
			.policies(companyDetailDto.getCompanyPolicies())
			.name(companyDetailDto.getCompanyName())
			.isConnect(companyDetailDto.getIsConnect())
			.build();
	}

	public static CompanyDetailResponse from(CompanyDto companyDto) {
		return CompanyDetailResponse
			.builder()
			.id(companyDto.getCompanyId())
			.name(companyDto.getCompanyName())
			.isConnect(companyDto.getIsConnect())
			.build();
	}
}
