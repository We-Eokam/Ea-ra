package com.eokam.proof.application.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<ProofDto> getMyProofList(String jwt) {
		String payload = jwt.split("\\.")[1];
		Long memberId = Long.parseLong(new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8));

		return proofRepository.findAllByMemberId(memberId)
			.stream()
			.map(ProofDto::from)
			.collect(Collectors.toList());
	}
}
