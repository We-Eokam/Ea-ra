package com.eokam.proof.application.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.domain.repository.ProofRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProofServiceImpl implements ProofService {

	private final ProofRepository proofRepository;

	@Override
	@Transactional
	public List<ProofDto> getMyProofList(String jwt, Integer page, Integer size) {
		String payload = jwt.split("\\.")[1];
		Long memberId = Long.parseLong(new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8));

		return proofRepository.findAllByMemberId(memberId, PageRequest.of(page, size))
			.stream()
			.map(ProofDto::from)
			.toList();
	}
}
