package com.eokam.proof.infrastructure.external.cpoint;

import com.eokam.proof.domain.constant.ActivityType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SaveCpointResponse(Long memberId, Long amount, ActivityType activityType, Long companyId) {
}
