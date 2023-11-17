package com.eokam.accusation.global.error;

import com.eokam.accusation.global.error.exception.FeignClientException;
import org.springframework.http.HttpStatus;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
	private ErrorDecoder errorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		FeignException exception = FeignException.errorStatus(methodKey, response);
		HttpStatus httpStatus = HttpStatus.valueOf(response.status());
		switch (response.status()){
			case 400:
				return new FeignClientException(ErrorCode.REJECT_BILL_USE);
			case 401:
				return new FeignClientException(ErrorCode.MEMBER_NOT_FOLLOWED);
			case 404:
				return new FeignClientException(ErrorCode.MEMBER_NOT_FOUND);
			default:
				if (httpStatus.is5xxServerError()) {
					return new RetryableException(
							response.status(),
							exception.getMessage(),
							response.request().httpMethod(),
							exception,
							null,
							response.request()
					);
				}
				return errorDecoder.decode(methodKey, response);
		}

	}
}
