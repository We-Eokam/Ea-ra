package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

public class JwtParseException extends RuntimeException{

	private ErrorCode errorCode;


	public JwtParseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
