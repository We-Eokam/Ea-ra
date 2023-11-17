package com.eokam.accusation.global.error.exception;

import com.eokam.accusation.global.error.ErrorCode;

public class FeignClientException extends BusinessException {

    public FeignClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
