package com.eokam.groo.application.service;

import java.util.List;

import com.eokam.groo.application.dto.GrooMonthDto;
import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.application.dto.GrooDailyDto;
import com.eokam.groo.infrastructure.dto.GrooTodayCountDto;

public interface GrooSavingService {

	GrooSavingDto createGrooSaving(GrooSavingDto grooSavingDto);

	GrooMonthDto getDailySavingAmountByMonth(Long memberId, Integer year, Integer month);

	List<GrooDailyDto> getDailyProofCountByWeek(Long memberId);

	GrooTodayCountDto getTodayCount(Long memberId);
}
