package com.eokam.accusation.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;

public interface AccusationService {

	AccusationDto createAccusation(AccusationDto accusationDto, List<MultipartFile> multipartFile);

	List<AccusationDto> getAccusationList(Long memberId);

	AccusationDto getAccusationDetail(Long accusationId);
}