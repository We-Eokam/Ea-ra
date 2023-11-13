package com.eokam.accusation.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	ACCUSATION_NOT_EXIST(HttpStatus.NOT_FOUND, "AC-001", "요청한 고발장이 존재하지 않습니다."),
	READ_PERMISSION_DENIED(HttpStatus.UNAUTHORIZED, "AC-002", "읽기 권한이 없습니다."),
	SELF_ACCUSATION_RESTRICTED(HttpStatus.UNAUTHORIZED, "AC-003", "자신이 자신에게 제보할 수 없습니다.");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
}
