package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

import lombok.Getter;

@Getter
public class NicknameAlreadyExistException extends BusinessException{

	private final ErrorCode errorCode;

	public NicknameAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
