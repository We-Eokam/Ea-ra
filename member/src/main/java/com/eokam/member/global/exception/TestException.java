package com.eokam.member.global.exception;

import com.eokam.member.global.ErrorCode;

public class TestException  extends BusinessException {

	private ErrorCode errorCode;


	public TestException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}
