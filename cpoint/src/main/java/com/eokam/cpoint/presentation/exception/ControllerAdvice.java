package com.eokam.cpoint.presentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.eokam.cpoint.global.exception.BusinessException;
import com.eokam.cpoint.global.exception.JwtException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(BindException.class)
	protected ResponseEntity<ErrorResponse> handleBindException(BindException exception) {
		log.error("handleBindException", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, "값을 바인딩 하는 동안 에러가 발생했습니다.");
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
		MethodArgumentTypeMismatchException exception) {
		log.error("handleMethodArgumentTypeMismatchException", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, exception.getName()+"의 타입이 일치하지 않습니다.");
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
		HttpRequestMethodNotSupportedException exception) {
		log.error("handleHttpRequestMethodNotSupportedException", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 Http Method입니다.");
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
		HttpMessageNotReadableException exception) {
		log.error("handleMissingServletRequestParameterException", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, "특정 Parameter가 존재하지 않습니다.");
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception exception) {
		log.error("handleIllegalArgumentException", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, HttpStatus.BAD_REQUEST, "올바르지 않은 식별자 입니다.");
		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<ErrorResponse> handleConflict(BusinessException exception) {
		log.error("BusinessException", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, exception.getErrorCode().getHttpStatus(), exception.getMessage());
		return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(errorResponse);
	}

	@ExceptionHandler(JwtException.class)
	protected ResponseEntity<ErrorResponse> handleConflict(JwtException exception) {
		log.error("BusinessException", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, exception.getErrorCode().getHttpStatus(), exception.getMessage());
		return ResponseEntity.status(exception.getErrorCode().getHttpStatus()).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(Exception exception) {
		log.error("Exception", exception);
		ErrorResponse errorResponse =
			ErrorResponse.create(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
		return ResponseEntity.internalServerError().body(errorResponse);
	}

}
