package com.eokam.cpoint.application.dto;

import java.util.List;

import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyPolicy;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyDetailDto {
	private Long companyId;

	private String companyName;

	private List<CompanyPolicyDto> companyPolicies;

	private Boolean isConnect;

	public static CompanyDetailDto of(Company company, List<CompanyPolicy> companyPolicies, Boolean isConnect) {
		return CompanyDetailDto
			.builder()
			.companyId(company.getId())
			.companyName(company.getName())
			.companyPolicies(companyPolicies.stream().map(CompanyPolicyDto::from).toList())
			.isConnect(isConnect)
			.build();
	}

	public static CompanyDetailDto of(CompanyDto companyDto, List<CompanyPolicy> companyPolicies) {
		return CompanyDetailDto
			.builder()
			.companyId(companyDto.getCompanyId())
			.companyName(companyDto.getCompanyName())
			.companyPolicies(companyPolicies.stream().map(CompanyPolicyDto::from).toList())
			.isConnect(companyDto.getIsConnect())
			.build();
	}
}
