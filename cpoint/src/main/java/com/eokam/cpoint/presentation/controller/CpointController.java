package com.eokam.cpoint.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.cpoint.application.dto.CpointDto;
import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.application.service.CpointService;
import com.eokam.cpoint.presentation.annotation.JwtMember;
import com.eokam.cpoint.presentation.dto.CpointCreateRequest;
import com.eokam.cpoint.presentation.dto.CpointCreateResponse;
import com.eokam.cpoint.presentation.dto.CpointResponse;
import com.eokam.cpoint.presentation.dto.CpointSummaryResponse;
import com.eokam.cpoint.presentation.dto.MemberDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cpoint")
@RequiredArgsConstructor
public class CpointController {

	private final CpointService cpointService;

	@GetMapping()
	public ResponseEntity<CpointResponse> retrieveCpoint(@JwtMember MemberDto memberDto) {
		Integer cpoint = cpointService.retrieveCpoint(memberDto);
		return ResponseEntity
			.ok(CpointResponse.builder()
				.cpoint(cpoint)
				.memberId(memberDto.getMemberId())
				.build());
	}

	@PostMapping()
	public ResponseEntity<CpointCreateResponse> saveCpoint(@JwtMember MemberDto memberDto,
		@Valid @RequestBody CpointCreateRequest cpointCreateRequest) {
		CpointDto cpointDto =
			cpointService.saveCpoint(CpointDto.of(cpointCreateRequest, memberDto));
		return ResponseEntity.ok(CpointCreateResponse.from(cpointDto));
	}

	@GetMapping("/summary")
	public ResponseEntity<CpointSummaryResponse> retrieveCpointSummary(@JwtMember MemberDto memberDto) {
		List<CpointSummaryDto> summaryList =
			cpointService.retrieveCpointSummary(memberDto);
		return ResponseEntity.ok(CpointSummaryResponse.builder().summaryList(summaryList).build());
	}
}
