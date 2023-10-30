package com.eokam.accusation.application.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.domain.entity.AccusationImage;
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
	public AccusationDto createAccusation(AccusationDto accusationDto, List<MultipartFile> multipartFile) throws
		IOException {
		Accusation accusation = accusationRepository.save(Accusation.from(accusationDto));
		List<String> fileUrls = s3UploadService.uploadFile(multipartFile);
		for (String fileUrl : fileUrls) {
			accusationImageRepository.save(AccusationImage.of(accusation, fileUrl));
		}
		return AccusationDto.of(accusation, fileUrls);
	}
}