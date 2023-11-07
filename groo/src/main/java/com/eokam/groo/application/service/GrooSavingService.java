package com.eokam.groo.application.service;

import java.util.List;

import com.eokam.groo.application.dto.GrooMonthDto;
import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.infrastructure.dto.WeeklyProofCountDto;

public interface GrooSavingService {

	GrooSavingDto createGrooSaving(GrooSavingDto grooSavingDto);

	GrooMonthDto getDailySavingAmountByMonth(Long memberId, Integer year, Integer month);

	List<WeeklyProofCountDto> getDailyProofCountByWeek(Long memberId);
}
