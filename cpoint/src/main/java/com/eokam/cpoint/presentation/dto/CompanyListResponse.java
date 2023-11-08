package com.eokam.cpoint.presentation.dto;

import java.util.List;

import com.eokam.cpoint.application.dto.CompanyDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CompanyListResponse {

	private List<CompanyDetailResponse> companyList;

	public static CompanyListResponse from(List<CompanyDto> companyList) {
		List<CompanyDetailResponse> companyDetailResponseList =
			companyList.stream().map(companyDto -> CompanyDetailResponse.from(companyDto)).toList();
		return CompanyListResponse.builder().companyList(companyDetailResponseList).build();
	}

}
