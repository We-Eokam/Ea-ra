package com.eokam.proof.presentation.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MyProofListResponse(List<ProofResponse> proof) {
	public static MyProofListResponse from(List<ProofResponse> proofResponses) {
		return MyProofListResponse.builder()
			.proof(proofResponses.stream().toList())
			.build();
	}
}
