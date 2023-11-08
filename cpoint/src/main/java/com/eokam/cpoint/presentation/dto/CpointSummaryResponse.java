package com.eokam.cpoint.presentation.dto;

import java.util.List;

import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CpointSummaryResponse {

	private List<CpointSummaryDto> summaryList;

}
