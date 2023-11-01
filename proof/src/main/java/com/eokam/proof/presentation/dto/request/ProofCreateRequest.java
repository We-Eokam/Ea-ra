package com.eokam.proof.presentation.dto.request;

import com.eokam.proof.domain.constant.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProofCreateRequest(ActivityType activityType, Long cCompanyId, String content) {
}
