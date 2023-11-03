package com.eokam.proof.presentation.dto.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.infrastructure.util.error.ErrorCode;
import com.eokam.proof.infrastructure.util.error.exception.ProofException;
import com.eokam.proof.presentation.dto.request.ProofCreateRequest;

@Component
public class ProofCreateRequestValidator {
	public void validate(ProofCreateRequest proofCreateRequest) {
		if (proofCreateRequest.cCompanyId() == null && StringUtils.isBlank(proofCreateRequest.content())) {
			throw new ProofException(ErrorCode.CREATE_PROOF_REQUIRE_ARG);
		}
		if (proofCreateRequest.cCompanyId() != null && StringUtils.isNotBlank(proofCreateRequest.content())) {
			throw new ProofException(ErrorCode.CREATE_PROOF_MANY_ARG);
		}
		if (!proofCreateRequest.activityType().equals(ActivityType.ETC) && proofCreateRequest.cCompanyId() == null) {
			throw new ProofException(ErrorCode.REQUIRE_CCOMPANY_ID);
		}
		if (proofCreateRequest.activityType().equals(ActivityType.ETC)
			&& StringUtils.isBlank(proofCreateRequest.content())) {
			throw new ProofException(ErrorCode.REQUIRE_CONTENT_ID);
		}
	}
}
