package com.eokam.proof.infrastructure.util.error.exception;

import com.eokam.proof.infrastructure.util.error.ErrorCode;

public class ProofException extends BusinessException {
	private final ErrorCode errorCode;

	public ProofException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
