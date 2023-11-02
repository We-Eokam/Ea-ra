package com.eokam.proof.infrastructure.util.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	S3_STORE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_001", "사진을 저장하는 동안 에러가 발생했습니다.");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}
