package com.eokam.accusation.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.application.service.AccusationService;
import com.eokam.accusation.presentation.dto.AccusationListResponse;
import com.eokam.accusation.presentation.dto.AccusationRequest;
import com.eokam.accusation.presentation.dto.AccusationResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accusation")
public class AccusationController {

	private final AccusationService accusationService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccusationResponse> createAccusation(
		@RequestPart(value = "file", required = false) List<MultipartFile> images,
		@RequestPart(value = "content") AccusationRequest request) {
		AccusationDto accusationDto = accusationService.createAccusation(AccusationDto.of(request), images);
		AccusationResponse response = AccusationResponse.from(accusationDto);
		return ResponseEntity.created(URI.create("/accusation/" + response.getAccusationId())).body(response);
	}

	@GetMapping
	public ResponseEntity<?> getAccusationList(@RequestParam Long memberId) {
		List<AccusationDto> accusationDtoList = accusationService.getAccusationList(memberId);
		return ResponseEntity.ok(AccusationListResponse.from(accusationDtoList));
	}
}