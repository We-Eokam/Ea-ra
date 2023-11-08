package com.eokam.cpoint.global.exception;

import com.eokam.cpoint.global.ErrorCode;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException {
	private final ErrorCode errorCode;

	public JwtException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
