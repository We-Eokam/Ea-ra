package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

public class LoginException extends RuntimeException{

	private ErrorCode errorCode;


	public LoginException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}
