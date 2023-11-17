package com.eokam.proof.infrastructure.rabbitmq.dto;


import java.time.LocalDateTime;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.presentation.dto.response.ProofResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class GrooSavingRequest {
	private Long memberId;

	private String savingType;

	private ActivityType activityType;

	private Long proofAccusationId;

	private LocalDateTime savedAt;

	public static GrooSavingRequest from(ProofResponse response) {
		return GrooSavingRequest.builder()
			.memberId(response.memberId())
			.savingType("PROOF")
			.activityType(response.activityType())
			.proofAccusationId(response.proofId())
			.savedAt(LocalDateTime.parse(response.createdAt()))
			.build();
	}
}
