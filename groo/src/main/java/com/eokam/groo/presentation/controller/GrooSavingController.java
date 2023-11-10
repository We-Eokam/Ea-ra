package com.eokam.groo.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import com.eokam.groo.global.validation.ValidationSequence;
import com.eokam.groo.infrastructure.dto.WeeklyProofCountDto;
import com.eokam.groo.infrastructure.jwt.TokenManager;
import com.eokam.groo.presentation.dto.GrooSavingMonthResponse;
import com.eokam.groo.presentation.dto.GrooSavingRequest;
import com.eokam.groo.presentation.dto.GrooSavingResponse;
import com.eokam.groo.presentation.dto.WeeklyProofCountResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/groo")
@RequiredArgsConstructor
public class GrooSavingController {

	private final GrooSavingService grooSavingService;
	private final TokenManager tokenManager;

	@PostMapping
	ResponseEntity<GrooSavingResponse> createGrooSaving(@RequestBody @Validated(ValidationSequence.class) GrooSavingRequest request) {
		GrooSavingDto grooSavingDto = grooSavingService.createGrooSaving(GrooSavingDto.of(request));
		ResponseEntity.created(URI.create("/groo/" + grooSavingDto.savingId())).body(GrooSavingResponse.from(grooSavingDto));
		return ResponseEntity.created(URI.create("/groo/" + grooSavingDto.savingId())).body(GrooSavingResponse.from(grooSavingDto));
	}

	@GetMapping
	ResponseEntity<GrooSavingMonthResponse> getDailySavingAmountByMonth(@CookieValue(value = "access-token") String jwt, @RequestParam Integer year, @RequestParam Integer month) {
		Long memberId = tokenManager.getMemberId(jwt);
		GrooMonthDto grooMonthDto = grooSavingService.getDailySavingAmountByMonth(memberId, year, month);
		return ResponseEntity.ok().body(GrooSavingMonthResponse.from(grooMonthDto));
	}

	@GetMapping("/current-week")
	ResponseEntity<WeeklyProofCountResponse> getDailyProofCountByWeek(@CookieValue(value = "access-token") String jwt){
		Long memberId = tokenManager.getMemberId(jwt);
		List<WeeklyProofCountDto> weeklyProofCountDtos = grooSavingService.getDailyProofCountByWeek(memberId);
		return ResponseEntity.ok().body(WeeklyProofCountResponse.from(weeklyProofCountDtos));
	}
}
