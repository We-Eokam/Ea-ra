package com.eokam.proof.application.service;

import java.util.List;
import java.util.stream.Collectors;

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
import com.eokam.proof.infrastructure.external.groo.GrooSaveRequest;
import com.eokam.proof.infrastructure.external.groo.GrooServiceFeign;
import com.eokam.proof.infrastructure.external.member.FollowList;
import com.eokam.proof.infrastructure.external.member.FollowServiceFeign;
import com.eokam.proof.infrastructure.external.member.IsFollowRequest;
import com.eokam.proof.infrastructure.external.s3.S3FileDetail;
import com.eokam.proof.infrastructure.external.s3.service.S3Service;
import com.eokam.proof.infrastructure.util.ParseJwtUtil;
import com.eokam.proof.infrastructure.util.error.ErrorCode;
import com.eokam.proof.infrastructure.util.error.exception.ProofException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProofServiceImpl implements ProofService {
	private final ProofRepository proofRepository;
	private final ProofImageRepository proofImageRepository;

	private final S3Service s3Service;

	private final FollowServiceFeign followServiceFeign;
	private final GrooServiceFeign grooServiceFeign;

	@Override
	public Page<ProofDto> getMyProofList(String jwt, PageRequest pageRequest) {
		Long memberId = ParseJwtUtil.parseMemberId(jwt);

		Page<Proof> proofPage = proofRepository.findAllByMemberId(memberId, pageRequest);

		return ProofDto.toDtoPage(proofPage);
	}

	@Override
	public Page<ProofDto> getProofList(String jwt, Long memberId, PageRequest pageRequest) {
		if (!isFriend(jwt, memberId)) {
			throw new ProofException(ErrorCode.PROOF_UNAUTHORIZED);
		}

		Page<Proof> proofPage = proofRepository.findAllByMemberId(memberId, pageRequest);

		return ProofDto.toDtoPage(proofPage);
	}

	@Override
	@Transactional
	public ProofDto createProof(String jwt, ProofCreateDto proofCreateDto, List<MultipartFile> multipartFiles) {
		if (!ParseJwtUtil.parseMemberId(jwt).equals(proofCreateDto.memberId())) {
			throw new ProofException(ErrorCode.PROOF_CREATE_UNAUTHORIZED);
		}

		Proof savedProof = proofRepository.save(Proof.from(proofCreateDto));

		List<S3FileDetail> s3SavedList = s3Service.saveList(multipartFiles);

		s3SavedList.forEach(file -> proofImageRepository.save(ProofImage.of(file, savedProof)));

		followServiceFeign.increaseAccusationCount(jwt, IsFollowRequest.from(proofCreateDto.memberId()));
		grooServiceFeign.saveGroo(jwt,
			GrooSaveRequest.of(ParseJwtUtil.parseMemberId(jwt), savedProof.getActivityType(), savedProof.getProofId(),
				savedProof.getCreatedAt()));

		return ProofDto.of(savedProof, s3SavedList);
	}

	@Override
	public ProofDto getProofDetail(String jwt, Long proofId) {
		Proof proof = proofRepository.findByProofId(proofId).orElseThrow(() -> new ProofException(
			ErrorCode.PROOF_NOT_EXIST));

		if (!isMeOrFriend(jwt, proof)) {
			throw new ProofException(ErrorCode.PROOF_UNAUTHORIZED);
		}

		return ProofDto.from(proof);
	}

	@Override
	public Page<ProofDto> getFeed(String jwt, PageRequest pageRequest) {
		FollowList followList = getFriends(jwt);

		List<Long> followIds = followList.followMemberList()
			.stream()
			.map(followMember -> followMember.memberProfile().memberId())
			.collect(Collectors.toList());

		followIds.add(ParseJwtUtil.parseMemberId(jwt));

		Page<Proof> proofPage = proofRepository.findAllByMemberList(followIds, pageRequest);

		return ProofDto.toDtoPage(proofPage);
	}

	@Override
	@Transactional
	public void deleteProof(String jwt, Long proofId) {
		Proof proof = proofRepository.findByProofId(proofId).orElseThrow(()
			-> new ProofException(ErrorCode.PROOF_NOT_EXIST));

		if (!proof.getMemberId().equals(ParseJwtUtil.parseMemberId(jwt))) {
			throw new ProofException(ErrorCode.PROOF_UNAUTHORIZED);
		}

		proofRepository.delete(proof);
	}

	private boolean isMeOrFriend(String jwt, Proof proof) {
		Long myId = ParseJwtUtil.parseMemberId(jwt);
		Long otherId = proof.getMemberId();

		if (myId.equals(otherId)) {
			return true;
		}

		return followServiceFeign.isFollow(jwt, otherId).followStatus().equals("ACCEPT");
	}

	private boolean isFriend(String jwt, Long memberId) {
		return followServiceFeign.isFollow(jwt, memberId).followStatus().equals("ACCEPT");
	}

	private FollowList getFriends(String jwt) {
		return followServiceFeign.getFriends(jwt);
	}
}
