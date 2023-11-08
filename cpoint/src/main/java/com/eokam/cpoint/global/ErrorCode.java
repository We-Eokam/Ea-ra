package com.eokam.cpoint.global;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	COMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMPANY_001", "존재하지 않는 회사입니다"),
	COMPANY_ALREADY_CONNECTED(HttpStatus.BAD_REQUEST, "COMPANY_002", "이미 연동된 회사입니다."),
	COMPANY_NOT_CONNECTED(HttpStatus.BAD_REQUEST, "COMPANY_003", "연동되지 않은 회사입니다."),
	JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT_001", "JWT 쿠키를 찾을 수 없습니다."),
	JWT_WRONG_VALUE(HttpStatus.UNAUTHORIZED,"JWT_002","잘못된 JWT 쿠키입니다.");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}
