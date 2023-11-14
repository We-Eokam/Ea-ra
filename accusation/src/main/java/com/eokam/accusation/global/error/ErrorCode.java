package com.eokam.accusation.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	ACCUSATION_NOT_EXIST(HttpStatus.NOT_FOUND, "AC-001", "요청한 고발장이 존재하지 않습니다."),
	READ_PERMISSION_DENIED(HttpStatus.UNAUTHORIZED, "AC-002", "읽기 권한이 없습니다."),
	SELF_ACCUSATION_RESTRICTED(HttpStatus.UNAUTHORIZED, "AC-003", "자신이 자신에게 제보할 수 없습니다."),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "F-001", "해당하는 멤버가 없습니다."),
	MEMBER_NOT_FOLLOWED(HttpStatus.BAD_REQUEST, "F-002", "서로 팔로우한 사용자가 아닙니다."),
	REJECT_BILL_USE(HttpStatus.FORBIDDEN, "F-003", "사용할 수 있는 고발장이 없습니다.")
	;
	
	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
}
