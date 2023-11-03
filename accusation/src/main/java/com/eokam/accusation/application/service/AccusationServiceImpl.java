package com.eokam.accusation.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.domain.entity.AccusationImage;
import com.eokam.accusation.global.error.ErrorCode;
import com.eokam.accusation.global.error.exception.BusinessException;
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

	@Override
	@Transactional
	public AccusationDto createAccusation(AccusationDto accusationDto, List<MultipartFile> multipartFile) {
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
	public List<AccusationDto> getAccusationList(Long memberId) {
		List<AccusationDto> accusationDtoList = new ArrayList<>();
		List<Accusation> accusations = accusationRepository.findByMemberId(memberId);
		for (Accusation accusation : accusations) {
			List<AccusationImage> accusationImages = accusationImageRepository.findByAccusation_AccusationId(
				accusation.getAccusationId());
			List<String> fileUrls = accusationImages.stream().map(AccusationImage::getFileUrl).toList();
			accusationDtoList.add(AccusationDto.of(accusation, fileUrls));
		}
		return accusationDtoList;
	}

	@Override
	public AccusationDto getAccusationDetail(Long accusationId) {
		Accusation accusation = accusationRepository.findByAccusationId(accusationId)
			.orElseThrow(() -> new BusinessException(ErrorCode.ACCUSATION_NOT_EXIST));
		List<AccusationImage> accusationImages = accusationImageRepository.findByAccusation_AccusationId(accusationId);
		List<String> fileUrls = accusationImages.stream().map(AccusationImage::getFileUrl).toList();
		return AccusationDto.of(accusation, fileUrls);
	}
}