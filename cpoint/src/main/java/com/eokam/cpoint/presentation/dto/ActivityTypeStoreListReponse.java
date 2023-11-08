package com.eokam.cpoint.presentation.dto;

import java.util.List;

import com.eokam.cpoint.application.dto.ActivityStroeClassDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ActivityTypeStoreListReponse {

	private List<ActivityStroeClassDto> storeList;

}
