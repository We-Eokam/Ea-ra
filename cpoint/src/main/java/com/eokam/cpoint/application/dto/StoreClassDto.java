package com.eokam.cpoint.application.dto;

import com.eokam.cpoint.domain.Company;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StoreClassDto {

	private Long companyId;

	private String companyName;

	private String branchName;

	private Double latitude;

	private Double longitude;

	private Double distance;

	public static StoreClassDto of(StoreDto storeDto, Company company) {
		return StoreClassDto
			.builder()
			.branchName(storeDto.getBranch())
			.companyId(company.getId())
			.companyName(company.getName())
			.distance(storeDto.getDistance())
			.latitude(storeDto.getLatitude())
			.longitude(storeDto.getLongitude())
			.build();
	}

}
