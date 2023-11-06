package com.eokam.groo.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.groo.application.dto.GrooMonthDto;
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
	ResponseEntity<GrooSavingResponse> createGrooSaving(@CookieValue(value = "access-token") String jwt, @RequestBody GrooSavingRequest request) {
		Long memberId = tokenManager.getMemberId(jwt);
		GrooSavingDto grooSavingDto = grooSavingService.createGrooSaving(GrooSavingDto.of(request, memberId));
		ResponseEntity.created(URI.create("/groo/" + grooSavingDto.savingId())).body(GrooSavingResponse.from(grooSavingDto));
		return ResponseEntity.created(URI.create("/groo/" + grooSavingDto.savingId())).body(GrooSavingResponse.from(grooSavingDto));
	}

	@GetMapping
	ResponseEntity<?> getDailySavingAmountByMonth(@CookieValue(value = "access-token") String jwt, @RequestParam Integer year, @RequestParam Integer month) {
		Long memberId = tokenManager.getMemberId(jwt);
		GrooMonthDto dailySavingAmountByMonth = grooSavingService.getDailySavingAmountByMonth(memberId, year, month);
		return ResponseEntity.ok().body(dailySavingAmountByMonth);
	}
}
