package com.eokam.proof.infrastructure.util.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	S3_STORE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_001", "사진을 저장하는 동안 에러가 발생했습니다."),
	CREATE_PROOF_MANY_ARG(HttpStatus.BAD_REQUEST, "PROOF_001", "너무 많은 인자가 전달되었습니다."),
	CREATE_PROOF_REQUIRE_ARG(HttpStatus.BAD_REQUEST, "PROOF_002", "전달 된 인자가 너무 적습니다."),
	REQUIRE_CCOMPANY_ID(HttpStatus.BAD_REQUEST, "PROOF_003", "c_company_id가 필요합니다"),
	REQUIRE_CONTENT_ID(HttpStatus.BAD_REQUEST, "PROOF_004", "content가 필요합니다");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}
