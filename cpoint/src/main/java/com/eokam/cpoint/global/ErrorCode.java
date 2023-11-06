package com.eokam.cpoint.global;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMPANY_001", "존재하지 않는 회사입니다");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}
