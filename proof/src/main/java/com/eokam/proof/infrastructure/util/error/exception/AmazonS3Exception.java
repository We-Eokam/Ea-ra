package com.eokam.proof.infrastructure.util.error.exception;

import com.eokam.proof.infrastructure.util.error.ErrorCode;

public class AmazonS3Exception extends BusinessException {
	private final ErrorCode errorCode;

	public AmazonS3Exception(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
