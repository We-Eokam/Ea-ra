package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

import lombok.Getter;

@Getter
public class LoginException extends BusinessException{

	private ErrorCode errorCode;


	public LoginException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

}
