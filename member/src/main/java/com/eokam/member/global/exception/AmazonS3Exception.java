package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

import lombok.Getter;

@Getter
public class AmazonS3Exception extends BusinessException {
	private final ErrorCode errorCode;

	public AmazonS3Exception(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
