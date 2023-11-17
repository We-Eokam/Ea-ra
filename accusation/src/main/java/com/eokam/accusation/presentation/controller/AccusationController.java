package com.eokam.accusation.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.application.dto.PageAccusationDto;
import com.eokam.accusation.application.service.AccusationService;
import com.eokam.accusation.global.config.RabbitSender;
import com.eokam.accusation.infrastructure.jwt.TokenManager;
import com.eokam.accusation.presentation.dto.AccusationListResponse;
import com.eokam.accusation.presentation.dto.AccusationRequest;
import com.eokam.accusation.presentation.dto.AccusationResponse;
import com.eokam.accusation.presentation.dto.GrooSavingRequest;
import com.eokam.accusation.presentation.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accusation")
public class AccusationController {

	private final AccusationService accusationService;
	private final RabbitSender rabbitSender;
	private final TokenManager tokenManager;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccusationResponse> createAccusation(@CookieValue(value = "access-token") String jwt,
		@RequestPart(value = "file", required = false) List<MultipartFile> images,
		@RequestPart(value = "content") AccusationRequest request) {
		Long memberId = tokenManager.getMemberId(jwt);
		AccusationDto accusationDto = accusationService.createAccusation(AccusationDto.of(request, memberId), images);
		AccusationResponse response = AccusationResponse.from(accusationDto);
		rabbitSender.send(GrooSavingRequest.from(response));
		rabbitSender.send(NotificationRequest.from(response));
		return ResponseEntity.created(URI.create("/accusation/" + response.getAccusationId())).body(response);
	}

	@GetMapping
	public ResponseEntity<?> getAccusationList(@CookieValue(value = "access-token") String jwt,
		@RequestParam Long targetId, @RequestParam Integer page,
		@RequestParam Integer size) {
		Long memberId = tokenManager.getMemberId(jwt);
		PageAccusationDto pageAccusationDto = accusationService.getAccusationList(targetId, memberId, page, size);
		return ResponseEntity.ok(AccusationListResponse.from(pageAccusationDto));
	}

	@GetMapping("/{accusationId}")
	public ResponseEntity<AccusationResponse> getAccusationDetail(@CookieValue(value = "access-token") String jwt,
		@PathVariable Long accusationId) {
		Long memberId = tokenManager.getMemberId(jwt);
		AccusationDto accusationDto = accusationService.getAccusationDetail(accusationId, memberId);
		return ResponseEntity.ok(AccusationResponse.from(accusationDto));
	}
}