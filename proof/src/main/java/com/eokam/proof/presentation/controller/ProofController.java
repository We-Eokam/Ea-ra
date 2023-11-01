package com.eokam.proof.presentation.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eokam.proof.application.service.ProofService;
import com.eokam.proof.presentation.dto.response.MyProofListResponse;
import com.eokam.proof.presentation.dto.response.ProofResponse;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/proof")
@RequiredArgsConstructor
public class ProofController {

	private final ProofService proofService;

	@GetMapping("/me")
	public ResponseEntity<MyProofListResponse> getMyProofList(
		@CookieValue("access-token") final String accessToken,
		@RequestParam @Min(value = 0, message = "page 값은 0 이상이어야 합니다.") final Integer page,
		@RequestParam @Min(value = 1, message = "size 값은 1 이상이어야 합니다.") final Integer size) {
		MyProofListResponse response = MyProofListResponse.from(
			proofService.getMyProofList(accessToken, PageRequest.of(page, size))
				.map(ProofResponse::from)
				.toList());

		if (response.proof().isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(response);
	}
}
