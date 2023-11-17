package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

import lombok.Getter;

@Getter
public class NoBillException extends BusinessException{

	private final ErrorCode errorCode;

	public NoBillException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
