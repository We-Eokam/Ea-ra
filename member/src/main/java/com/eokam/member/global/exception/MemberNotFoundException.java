package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends BusinessException{

	private final ErrorCode errorCode;

	public MemberNotFoundException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}


}
