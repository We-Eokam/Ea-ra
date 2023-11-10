package com.eokam.notification.infrastructure.accusation.dto;

import com.eokam.notification.infrastructure.accusation.constant.AccusationType;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccusationRequest(Long sender, Long receiver, AccusationType accusationType) {
}
