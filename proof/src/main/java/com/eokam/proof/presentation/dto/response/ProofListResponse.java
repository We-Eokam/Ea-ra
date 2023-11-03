package com.eokam.proof.presentation.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProofListResponse(List<ProofResponse> proof) {
	public static ProofListResponse from(List<ProofResponse> proofResponses) {
		return ProofListResponse.builder()
			.proof(proofResponses.stream().toList())
			.build();
	}
}
