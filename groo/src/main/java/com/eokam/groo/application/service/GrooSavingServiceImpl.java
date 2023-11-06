package com.eokam.groo.application.service;

import org.springframework.stereotype.Service;

import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.infrastructure.repository.GrooSavingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrooSavingServiceImpl implements GrooSavingService{

	private final GrooSavingRepository grooSavingRepository;

	@Override
	public GrooSavingDto createGrooSaving(GrooSavingDto grooSavingDto) {
		GrooSaving grooSaving = grooSavingRepository.save(GrooSaving.from(grooSavingDto));
		return GrooSavingDto.from(grooSaving);
	}
}
