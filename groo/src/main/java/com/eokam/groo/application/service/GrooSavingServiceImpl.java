package com.eokam.groo.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eokam.groo.application.dto.GrooMonthDto;
import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto;
import com.eokam.groo.infrastructure.dto.GrooMonthSumAmountDto;
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

	@Override
	public GrooMonthDto getDailySavingAmountByMonth(Long memberId, Integer year, Integer month) {
		List<GrooDailySumAmountDto> dailySumAndAmount = grooSavingRepository.getDailySumAndAmount(memberId, year,
			month);
		GrooMonthSumAmountDto grooMonthSumAmountDto = grooSavingRepository.getSumAndAmountByMonth(memberId, year, month);
		return GrooMonthDto.of(dailySumAndAmount, grooMonthSumAmountDto);
	}
}
