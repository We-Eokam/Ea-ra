package com.eokam.proof.presentation.dto.validator;

import org.springframework.stereotype.Component;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.infrastructure.util.error.ErrorCode;
import com.eokam.proof.infrastructure.util.error.exception.ProofException;
import com.eokam.proof.presentation.dto.request.ProofCreateRequest;

@Component
public class ProofCreateRequestValidator {
	public void validate(ProofCreateRequest proofCreateRequest) {
		if (proofCreateRequest.cCompanyId() == null && proofCreateRequest.content().isBlank()) {
			throw new ProofException(ErrorCode.CREATE_PROOF_MANY_ARG);
		}
		if (proofCreateRequest.cCompanyId() != null && !proofCreateRequest.content().isBlank()) {
			throw new ProofException(ErrorCode.CREATE_PROOF_REQUIRE_ARG);
		}
		if (!proofCreateRequest.activityType().equals(ActivityType.ETC) && proofCreateRequest.cCompanyId() == null) {
			throw new ProofException(ErrorCode.REQUIRE_CCOMPANY_ID);
		}
		if (proofCreateRequest.activityType().equals(ActivityType.ETC) && proofCreateRequest.content().isBlank()) {
			throw new ProofException(ErrorCode.REQUIRE_CONTENT_ID);
		}
	}
}
