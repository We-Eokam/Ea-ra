package com.eokam.proof.presentation.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.PageRequest;
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

import com.eokam.proof.application.dto.ProofCreateDto;
import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.application.service.ProofServiceImpl;
import com.eokam.proof.presentation.dto.request.ProofCreateRequest;
import com.eokam.proof.presentation.dto.response.ProofListResponse;
import com.eokam.proof.presentation.dto.response.ProofResponse;
import com.eokam.proof.presentation.dto.validator.ProofCreateRequestValidator;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/proof")
@RequiredArgsConstructor
public class ProofController {

	private final ProofServiceImpl proofService;
	private final ProofCreateRequestValidator proofCreateRequestValidator;

	@GetMapping("/me")
	public ResponseEntity<ProofListResponse> getMyProofList(
		@CookieValue("access-token") final String accessToken,
		@RequestParam @Min(value = 0, message = "page 값은 0 이상이어야 합니다.") final Integer page,
		@RequestParam @Min(value = 1, message = "size 값은 1 이상이어야 합니다.") final Integer size) {
		ProofListResponse response = ProofListResponse.from(
			proofService.getMyProofList(accessToken, PageRequest.of(page, size))
				.map(ProofResponse::from)
				.toList());

		if (response.proof().isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(response);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProofResponse> postCreateProof(@CookieValue("access-token") final String accessToken,
		@RequestPart(value = "file", required = false) List<MultipartFile> images,
		@RequestPart(value = "content") @Valid ProofCreateRequest request
	) {
		proofCreateRequestValidator.validate(request);

		ProofResponse response = ProofResponse.from(
			proofService.createProof(accessToken, ProofCreateDto.of(accessToken, request), images));

		return ResponseEntity.created(URI.create("/proof/" + response.proofId())).body(response);
	}

	@GetMapping("/{proofId}")
	public ResponseEntity<ProofResponse> getProofDetail(
		@CookieValue("access-token") final String accessToken,
		@PathVariable Long proofId) {
		ProofDto response = proofService.getProofDetail(accessToken, proofId);

		return ResponseEntity.ok().body(ProofResponse.from(response));
	}

	@GetMapping
	public ResponseEntity<ProofListResponse> getFriendsProofList(
		@CookieValue("access-token") final String accessToken,
		@RequestParam final Long memberId,
		@RequestParam @Min(value = 0, message = "page 값은 0 이상이어야 합니다.") final Integer page,
		@RequestParam @Min(value = 1, message = "size 값은 1 이상이어야 합니다.") final Integer size) {
		ProofListResponse response = ProofListResponse.from(
			proofService.getProofList(accessToken, memberId, PageRequest.of(page, size))
				.map(ProofResponse::from)
				.toList());

		if (response.proof().isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/feed")
	public ResponseEntity<ProofListResponse> getFeedList(
		@CookieValue("access-token") final String accessToken,
		@RequestParam @Min(value = 0, message = "page 값은 0 이상이어야 합니다.") final Integer page,
		@RequestParam @Min(value = 1, message = "size 값은 1 이상이어야 합니다.") final Integer size) {
		ProofListResponse response = ProofListResponse.from(
			proofService.getFeed(accessToken, PageRequest.of(page, size))
				.map(ProofResponse::from)
				.toList());

		if (response.proof().isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body(response);
	}
}
