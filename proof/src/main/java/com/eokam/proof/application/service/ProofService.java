package com.eokam.proof.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.repository.ProofImageRepository;
import com.eokam.proof.domain.repository.ProofRepository;
import com.eokam.proof.infrastructure.util.ParseJwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProofService {
	private final ProofRepository proofRepository;
	private final ProofImageRepository proofImageRepository;

	public Page<ProofDto> getMyProofList(String jwt, PageRequest pageRequest) {
		Long memberId = ParseJwtUtil.parseMemberId(jwt);

		Page<Proof> proofPage = proofRepository.findAllByMemberId(memberId, pageRequest);

		return ProofDto.toDtoPage(proofPage);
	}
}
