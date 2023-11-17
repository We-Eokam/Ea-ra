package com.eokam.member.presentation.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.member.application.service.LoginService;
import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.JwtParseException;
import com.eokam.member.infra.external.service.OauthProvider;
import com.eokam.member.presentation.dto.MemberDetailResponse;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	private final OauthProvider oauthProvider;

	@Value("${cookie.domain}")
	private String domain;

	@GetMapping("/login")
	public ResponseEntity<?> login(@CookieValue(value="access-token",required = false) String accessToken){
		if(accessToken != null){
			MemberDetailResponse memberDetailResponse =
				MemberDetailResponse.from(loginService.loginWithToken(accessToken));
			return ResponseEntity.ok(memberDetailResponse);
		}
		else{
			return new ResponseEntity<>(oauthProvider.retrieveLoginRedirectHeader(), HttpStatus.MOVED_PERMANENTLY);
		}
	}

	@GetMapping("/login/oauth/kakao")
	public ResponseEntity<?> loginDone(@RequestParam(value = "code", required = false) String authorizeCode){
		String accessToken = loginService.login(authorizeCode);
		ResponseCookie responseCookie = ResponseCookie.from("access-token",accessToken)
		    .sameSite("None")
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(180000)
			.build();
		return new ResponseEntity<>(oauthProvider
			.retrieveClientRedirectHeader(responseCookie.toString()),HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping("/logout")
	public ResponseEntity<?> loginOut(@CookieValue(value="access-token",required = false) String accessToken){
		if(accessToken == null){
			throw new JwtParseException(ErrorCode.JWT_PARSE_FAIL);
		}
		loginService.logout(accessToken);
		ResponseCookie responseCookie = ResponseCookie.from("access-token",accessToken)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(0)
			.build();
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
			.build();
	}
}
