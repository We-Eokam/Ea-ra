package com.eokam.proof.infrastructure.external.groo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GrooSaveResponse(Long savingId, Long memberId, Long amount, String savingType, Long proofAccusationId,
							   String activityType, Long remainGroo, LocalDateTime savedAt) {
}
