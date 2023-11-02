package com.eokam.proof.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.proof.application.dto.ProofCreateDto;
import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofImageRepository;
import com.eokam.proof.domain.repository.ProofRepository;
import com.eokam.proof.infrastructure.external.s3.S3FileDetail;
import com.eokam.proof.infrastructure.external.s3.service.S3Service;
import com.eokam.proof.infrastructure.util.ParseJwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProofService {
	private final ProofRepository proofRepository;
	private final ProofImageRepository proofImageRepository;

	private final S3Service s3Service;

	public Page<ProofDto> getMyProofList(String jwt, PageRequest pageRequest) {
		Long memberId = ParseJwtUtil.parseMemberId(jwt);

		Page<Proof> proofPage = proofRepository.findAllByMemberId(memberId, pageRequest);

		return ProofDto.toDtoPage(proofPage);
	}

	@Transactional
	public ProofDto createProof(ProofCreateDto proofCreateDto, List<MultipartFile> multipartFiles) {

		Proof savedProof = proofRepository.save(Proof.from(proofCreateDto));

		List<S3FileDetail> s3SavedList = s3Service.saveList(multipartFiles);

		s3SavedList.forEach(file -> proofImageRepository.save(ProofImage.of(file, savedProof)));

		return ProofDto.of(savedProof, s3SavedList);
	}
}
