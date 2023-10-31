package com.eokam.proof.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.proof.application.service.ProofService;
import com.eokam.proof.presentation.dto.MyProofListResponse;
import com.eokam.proof.presentation.dto.ProofResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/proof")
@RequiredArgsConstructor
public class ProofController {

	private final ProofService proofService;

	@GetMapping("/me")
	public ResponseEntity<MyProofListResponse> getMyProofList(
		@CookieValue("access-token") final String accessToken,
		@RequestParam final Integer page,
		@RequestParam final Integer size) {
		MyProofListResponse response = MyProofListResponse.from(
			proofService.getMyProofList(accessToken, page, size).stream()
				.map(ProofResponse::from)
				.toList());

		return ResponseEntity.ok().body(response);
	}
}
