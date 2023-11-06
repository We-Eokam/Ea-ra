package com.eokam.groo.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.application.service.GrooSavingService;
import com.eokam.groo.infrastructure.jwt.TokenManager;
import com.eokam.groo.presentation.dto.GrooSavingRequest;
import com.eokam.groo.presentation.dto.GrooSavingResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groo")
@RequiredArgsConstructor
public class GrooSavingController {

	private final GrooSavingService grooSavingService;
	private final TokenManager tokenManager;

	@PostMapping
	ResponseEntity<?> createGrooSaving(@CookieValue(value = "access-token") String jwt, @RequestBody GrooSavingRequest request) {
		Long memberId = tokenManager.getMemberId(jwt);
		GrooSavingDto grooSavingDto = grooSavingService.createGrooSaving(GrooSavingDto.of(request, memberId));
		return ResponseEntity.created(URI.create("/groo/" + grooSavingDto.savingId())).body(GrooSavingResponse.from(grooSavingDto));
	}
}
