package com.eokam.groo.application.service;

import com.eokam.groo.application.dto.GrooMonthDto;
import com.eokam.groo.application.dto.GrooSavingDto;

public interface GrooSavingService {

	GrooSavingDto createGrooSaving(GrooSavingDto grooSavingDto);

	GrooMonthDto getDailySavingAmountByMonth(Long memberId, Integer year, Integer month);
}
