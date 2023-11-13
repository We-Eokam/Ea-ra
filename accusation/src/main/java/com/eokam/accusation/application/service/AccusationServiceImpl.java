package com.eokam.accusation.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.application.dto.PageAccusationDto;
import com.eokam.accusation.application.dto.PageInfoDto;
import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.domain.entity.AccusationImage;
import com.eokam.accusation.global.error.ErrorCode;
import com.eokam.accusation.global.error.exception.BusinessException;
import com.eokam.accusation.infrastructure.client.MemberServiceClient;
import com.eokam.accusation.infrastructure.client.dto.MemberClientRequest;
import com.eokam.accusation.infrastructure.repository.AccusationImageRepository;
import com.eokam.accusation.infrastructure.repository.AccusationRepository;
import com.eokam.accusation.infrastructure.service.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccusationServiceImpl implements AccusationService {

	private final AccusationRepository accusationRepository;
	private final AccusationImageRepository accusationImageRepository;

	private final S3Service s3UploadService;
	private final MemberServiceClient memberServiceClient;

	@Override
	@Transactional
	public AccusationDto createAccusation(AccusationDto accusationDto, List<MultipartFile> multipartFile) {
		if (accusationDto.witnessId().equals(accusationDto.memberId())) {
			throw new BusinessException(ErrorCode.SELF_ACCUSATION_RESTRICTED);
		}
		memberServiceClient.isValidRequest(MemberClientRequest.from(accusationDto));
		List<String> fileUrls = getFileUrls(multipartFile);
		Accusation accusation = accusationRepository.save(Accusation.from(accusationDto));
		for (String fileUrl : fileUrls) {
			accusationImageRepository.save(AccusationImage.of(accusation, fileUrl));
		}
		return AccusationDto.of(accusation, fileUrls);
	}

	public List<String> getFileUrls(List<MultipartFile> multipartFile) {
		try {
			return s3UploadService.uploadFile(multipartFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PageAccusationDto getAccusationList(Long targetId, Long memberId, Integer page, Integer size) {
		Page<Accusation> pageAccusationList = null;
		if (targetId.equals(memberId)) {
			pageAccusationList = accusationRepository.findByMemberId(targetId,
				PageRequest.of(page, size,
					Sort.by("accusationId").descending()));
		} else {
			pageAccusationList = accusationRepository.findByMemberIdAndWitnessId(targetId, memberId,
				PageRequest.of(page, size,
					Sort.by("accusationId").descending()));
		}
		List<AccusationDto> accusationDtoList = new ArrayList<>();
		List<Accusation> accusations = pageAccusationList.getContent();
		PageInfoDto pageInfoDto = PageInfoDto.of(pageAccusationList.isLast(), pageAccusationList.getTotalPages(),
			pageAccusationList.getTotalElements());
		for (Accusation accusation : accusations) {
			List<AccusationImage> accusationImages = accusationImageRepository.findByAccusation_AccusationId(
				accusation.getAccusationId());
			List<String> fileUrls = accusationImages.stream().map(AccusationImage::getFileUrl).toList();
			accusationDtoList.add(AccusationDto.of(accusation, fileUrls));
		}
		return PageAccusationDto.of(pageInfoDto, accusationDtoList);
	}

	@Override
	public AccusationDto getAccusationDetail(Long accusationId, Long memberId) {
		Accusation accusation = accusationRepository.findByAccusationId(accusationId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ACCUSATION_NOT_EXIST));
		if (!memberId.equals(accusation.getMemberId()) && !memberId.equals(accusation.getWitnessId())) {
			throw new BusinessException(ErrorCode.READ_PERMISSION_DENIED);
		}
		List<AccusationImage> accusationImages = accusationImageRepository.findByAccusation_AccusationId(accusationId);
		List<String> fileUrls = accusationImages.stream().map(AccusationImage::getFileUrl).toList();
		return AccusationDto.of(accusation, fileUrls);
	}

}