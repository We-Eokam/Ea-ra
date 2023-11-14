package com.eokam.proof.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.proof.application.dto.ProofCreateDto;
import com.eokam.proof.application.dto.ProofDto;

public interface ProofService {
	Page<ProofDto> getMyProofList(String jwt, PageRequest pageRequest);

	Page<ProofDto> getProofList(String jwt, Long memberId, PageRequest pageRequest);

	ProofDto createProof(String jwt, ProofCreateDto proofCreateDto, List<MultipartFile> multipartFiles);

	ProofDto getProofDetail(String jwt, Long proofId);

	Page<ProofDto> getFeed(String jwt, PageRequest pageRequest);

	void deleteProof(String jwt, Long proofId);
}
