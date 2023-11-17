package com.eokam.accusation.application.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.application.dto.PageAccusationDto;

public interface AccusationService {

	AccusationDto createAccusation(AccusationDto accusationDto, List<MultipartFile> multipartFile);

	PageAccusationDto getAccusationList(Long memberId, Long witnessId, Integer page, Integer size);

	AccusationDto getAccusationDetail(Long accusationId, Long memberId);
}