package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

import lombok.Getter;

@Getter
public class JwtParseException extends BusinessException{

	private ErrorCode errorCode;


	public JwtParseException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
