package com.eokam.notification.presentation.dto.accusation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccusationRequest(Long sender, Long receiver, AccusationType accusationType) {
}
