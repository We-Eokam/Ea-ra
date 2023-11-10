package com.eokam.member.global;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"MEM_001","해당하는 멤버가 없읍니다."),
	BILL_NOT_ENOUGH(HttpStatus.BAD_REQUEST,"BILL_001","고소장이 없읍니다."),
	S3_NOT_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR,"S3_001","S3에 파일 업로드가 실패합니다."),
	NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"NICKNAME_001","이미 존재하는 닉네임입니다.");
	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}

