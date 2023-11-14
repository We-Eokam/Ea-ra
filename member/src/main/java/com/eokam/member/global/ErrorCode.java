package com.eokam.member.global;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"MEM_001","해당하는 멤버가 없읍니다."),
	BILL_NOT_ENOUGH(HttpStatus.BAD_REQUEST,"BILL_001","고소장이 없읍니다."),
	TEST_ALREADY_DONE(HttpStatus.BAD_REQUEST,"TEST_001","테스트를 이미 완료한 사용자입니다."),
	S3_NOT_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR,"S3_001","S3에 파일 업로드가 실패합니다."),
	NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"NICKNAME_001","이미 존재하는 닉네임입니다."),
	TOKEN_ALREADY_LOGOUT(HttpStatus.UNAUTHORIZED,"LOGIN_001","이미 로그아웃된 토큰입니다."),
	KAKAO_TOKEN_REQUEST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"LOGIN_002","카카오 access token 요청이 실패했습니다."),
	KAKAO_SERVER_ERROR(HttpStatus.BAD_REQUEST,"LOGIN_003","카카오 서버의 문제로 로그인에 실패했습니다"),
	KAKAO_MEMBER_REQUEST_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"LOGIN_004","카카오 정보 요청이 실패했습니다."),
	JWT_PARSE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"JWT_001","JWT 토큰 파싱 과정에서 오류가 발생했습니다."),
	INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED,"JWT_002","유효하지 않은 JWT 토큰입니다."),
	NO_JWT_TOKEN(HttpStatus.UNAUTHORIZED,"JWT_003","JWT 토큰이 존재하지 않습니다."),
	ALREADY_FOLLOW(HttpStatus.BAD_REQUEST,"FOLLOW_001","이미 follow한 사용자 입니다."),
	THEY_NOT_FOLLOW(HttpStatus.BAD_REQUEST,"FOLLOW_003","서로 팔로우한 사용자가 아닙니다.(친구사이가 아닙니다)"),
	NO_FOLLOW_REQUEST(HttpStatus.BAD_REQUEST,"FOLLOW_002","해당 유저가 보낸 팔로우 신청이 없습니다. requestorId와 receiverId를 확인해주세요");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String errorCode;
	private final String message;
}

