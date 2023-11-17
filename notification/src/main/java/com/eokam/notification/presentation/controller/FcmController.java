package com.eokam.notification.presentation.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eokam.notification.application.notification.dto.NotificationDto;
import com.eokam.notification.application.notification.service.NotificationService;
import com.eokam.notification.application.token.dto.TokenDto;
import com.eokam.notification.application.token.service.FcmTokenService;
import com.eokam.notification.infrastructure.fcm.service.FcmMessageService;
import com.eokam.notification.infrastructure.member.MemberServiceFeign;
import com.eokam.notification.infrastructure.util.ParseJwtUtil;
import com.eokam.notification.presentation.dto.NotificationResponseList;
import com.eokam.notification.presentation.dto.TokenRequest;
import com.eokam.notification.presentation.dto.accusation.AccusationRequest;
import com.eokam.notification.presentation.dto.follow.FollowRequest;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class FcmController {

	private final FcmTokenService fcmService;
	private final FcmMessageService fcmMessageService;
	private final NotificationService notificationService;
	private final MemberServiceFeign memberServiceFeign;

	@PostMapping
	public ResponseEntity<Void> registerToken(@CookieValue("access-token") final String accessToken,
		@RequestBody final TokenRequest tokenRequest) {
		fcmService.register(TokenDto.of(accessToken, tokenRequest));

		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteToken(@CookieValue("access-token") final String accessToken) {
		fcmService.delete(accessToken);

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<NotificationResponseList> getNotification(
		@CookieValue("access-token") final String accessToken,
		@RequestParam final LocalDate startDate, @RequestParam final LocalDate endDate) {
		List<NotificationDto> notifications = notificationService.getNotification(accessToken, startDate, endDate);

		return ResponseEntity.ok(NotificationResponseList.from(notifications));
	}

	@PostMapping("/follow")
	public ResponseEntity<NotificationResponseList> sendFollow(
		@CookieValue("access-token") final String accessToken,
		@RequestBody FollowRequest followRequest) throws FirebaseMessagingException {
		if (!ParseJwtUtil.parseMemberId(accessToken).equals(followRequest.sender())) {
			return ResponseEntity.badRequest().build();
		}

		NotificationDto notificationDto = notificationService.saveFollowNotification(
			NotificationDto.follow(followRequest));

		String prefix =
			memberServiceFeign.getMemberDetail(followRequest.sender()).memberList().get(0).nickname() + "님이";

		fcmMessageService.sendMessageTo(
			fcmService.getToken(notificationDto.getReceiver()).token(),
			"친구 요청",
			prefix + notificationDto.getContent()
		);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/follow-accept")
	public ResponseEntity<NotificationResponseList> acceptFollow(
		@CookieValue("access-token") final String accessToken,
		@RequestBody FollowRequest followRequest) throws FirebaseMessagingException {
		if (!ParseJwtUtil.parseMemberId(accessToken).equals(followRequest.sender())) {
			return ResponseEntity.badRequest().build();
		}

		NotificationDto notificationDto = notificationService.saveFollowAcceptNotification(
			NotificationDto.followAccept(followRequest));

		String prefix =
			memberServiceFeign.getMemberDetail(followRequest.sender()).memberList().get(0).nickname() + "님이";

		fcmMessageService.sendMessageTo(
			fcmService.getToken(notificationDto.getReceiver()).token(),
			"친구 요청 수락",
			prefix + notificationDto.getContent()
		);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/accusation")
	public ResponseEntity<Void> sendAccusation(@CookieValue("access-token") final String accessToken,
		@RequestBody AccusationRequest accusationRequest) throws FirebaseMessagingException {
		if (!ParseJwtUtil.parseMemberId(accessToken).equals(accusationRequest.sender())) {
			return ResponseEntity.badRequest().build();
		}

		NotificationDto notificationDto = notificationService.saveAccusationNotification(
			NotificationDto.accusation(accusationRequest));

		String prefix =
			memberServiceFeign.getMemberDetail(accusationRequest.sender()).memberList().get(0).nickname() + "님이";

		fcmMessageService.sendMessageTo(
			fcmService.getToken(notificationDto.getReceiver()).token(),
			"환경 오염 제보",
			prefix + notificationDto.getContent()
		);

		return ResponseEntity.ok().build();
	}
}
