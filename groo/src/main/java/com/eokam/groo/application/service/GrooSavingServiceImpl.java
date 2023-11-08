package com.eokam.groo.application.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.groo.application.dto.GrooMonthDto;
import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.infrastructure.dto.WeeklyProofCountDto;
import com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto;
import com.eokam.groo.infrastructure.dto.GrooMonthSumAmountDto;
import com.eokam.groo.infrastructure.repository.GrooSavingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrooSavingServiceImpl implements GrooSavingService{

	private final GrooSavingRepository grooSavingRepository;

	@Override
	@Transactional
	public GrooSavingDto createGrooSaving(GrooSavingDto grooSavingDto) {
		GrooSaving grooSaving = grooSavingRepository.save(GrooSaving.from(grooSavingDto));
		return GrooSavingDto.from(grooSaving);
	}

	@Override
	@Transactional(readOnly = true)
	public GrooMonthDto getDailySavingAmountByMonth(Long memberId, Integer year, Integer month) {
		List<GrooDailySumAmountDto> dailySumAndAmount = grooSavingRepository.getDailySumAndAmount(memberId, year,
			month);
		GrooMonthSumAmountDto grooMonthSumAmountDto = grooSavingRepository.getSumAndAmountByMonth(memberId, year, month);
		return GrooMonthDto.of(dailySumAndAmount, grooMonthSumAmountDto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<WeeklyProofCountDto> getDailyProofCountByWeek(Long memberId) {
		LocalDate today = LocalDate.now();
		LocalDate startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		LocalDate endDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
		return grooSavingRepository.getDailyProofCount(memberId, startDate, endDate);
	}
}
