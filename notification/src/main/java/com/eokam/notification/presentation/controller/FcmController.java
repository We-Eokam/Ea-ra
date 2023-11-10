package com.eokam.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eokam.notification.application.dto.TokenDto;
import com.eokam.notification.application.service.FcmTokenService;
import com.eokam.notification.infrastructure.fcm.service.FcmMessageService;
import com.eokam.notification.presentation.dto.TokenRequest;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class FcmController {

	private final FcmTokenService fcmService;
	private final FcmMessageService fcmMessageService;

	@PostMapping
	public ResponseEntity<Void> registerToken(@RequestHeader("Authorization") final String accessToken,
		@RequestBody TokenRequest tokenRequest) {
		fcmService.register(TokenDto.of(accessToken, tokenRequest));

		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> registerToken(@RequestHeader("Authorization") final String accessToken) {
		fcmService.delete(accessToken);

		return ResponseEntity.ok().build();
	}

}
