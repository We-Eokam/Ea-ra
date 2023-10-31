package com.eokam.proof.infrastructure.util.error.exception;

import com.eokam.proof.infrastructure.util.error.ErrorCode;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
