package com.eokam.cpoint.application.dto;

import com.eokam.cpoint.domain.Company;

import lombok.Getter;

@Getter
public class CompanyDto {

	private Long companyId;

	private String companyName;

	private Boolean isConnect;

	public CompanyDto(Long companyId, String companyName, Boolean isConnect) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.isConnect = isConnect;
	}

	public static CompanyDto of(Company company, Boolean isConnect) {
		return new CompanyDto(company.getId(), company.getName(), isConnect);
	}

}
