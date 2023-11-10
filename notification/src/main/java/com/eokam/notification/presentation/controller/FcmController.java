package com.eokam.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eokam.notification.application.notification.dto.AccusationDto;
import com.eokam.notification.application.notification.service.NotificationService;
import com.eokam.notification.application.token.dto.TokenDto;
import com.eokam.notification.application.token.service.FcmTokenService;
import com.eokam.notification.infrastructure.accusation.dto.AccusationRequest;
import com.eokam.notification.infrastructure.fcm.service.FcmMessageService;
import com.eokam.notification.infrastructure.util.ParseJwtUtil;
import com.eokam.notification.presentation.dto.TokenRequest;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class FcmController {

	private final FcmTokenService fcmService;
	private final FcmMessageService fcmMessageService;
	private final NotificationService notificationService;

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

	@PostMapping("/accusation")
	public ResponseEntity<Void> sendAccusation(@RequestHeader("Authorization") final String accessToken,
		@RequestBody AccusationRequest accusationRequest) throws FirebaseMessagingException {
		if (!ParseJwtUtil.parseMemberId(accessToken).equals(accusationRequest.sender())) {
			return ResponseEntity.badRequest().build();
		}

		AccusationDto accusationDto = notificationService.sendAccusation(AccusationDto.from(accusationRequest));
		fcmMessageService.sendMessageTo(
			fcmService.getToken(accusationDto.receiver()).token(),
			"환경 오염 제보",
			accusationDto.content()
		);

		return ResponseEntity.ok().build();
	}
}
