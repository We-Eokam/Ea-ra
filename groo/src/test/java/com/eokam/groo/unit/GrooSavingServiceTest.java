package com.eokam.groo.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.eokam.groo.application.dto.GrooMonthDto;
import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.application.dto.GrooDailyDto;
import com.eokam.groo.application.service.GrooSavingServiceImpl;
import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto;
import com.eokam.groo.infrastructure.dto.GrooMonthSumAmountDto;
import com.eokam.groo.infrastructure.dto.WeeklyProofCountDto;
import com.eokam.groo.infrastructure.repository.GrooSavingRepository;

@ActiveProfiles("test")
public class GrooSavingServiceTest {

	@Mock
	private GrooSavingRepository grooSavingRepository;

	@InjectMocks
	private GrooSavingServiceImpl grooSavingService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("그루 적립 내역을 저장한다.")
	void createGrooSaving() {
		// given
		LocalDateTime localDateTime = LocalDateTime.parse("2023-08-08T12:49:00.2511049");
		GrooSaving grooSaving = GrooSaving.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.amount(ActivityType.DISPOSABLE_CUP.getSavingAmount())
			.savedAt(localDateTime)
			.proofAccusationId(2L)
			.remainGroo(100L)
			.build();
		GrooSavingDto grooSavingDto = GrooSavingDto.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.amount(ActivityType.DISPOSABLE_CUP.getSavingAmount())
			.savedAt(localDateTime)
			.proofAccusationId(2L)
			.remainGroo(100L)
			.build();
		given(grooSavingRepository.save(any())).willReturn(grooSaving);

		// when
		GrooSavingDto getGrooSavingDto = grooSavingService.createGrooSaving(grooSavingDto);

		// then
		verify(grooSavingRepository).save(any());
		assertThat(getGrooSavingDto.savedAt()).isEqualTo(grooSavingDto.savedAt());
	}

	@Test
	@DisplayName("특정 월의 그린 적립 내역을 조회할 수 있다.")
	void getDailySavingAmountByMonth() {
		//given
		List<GrooDailySumAmountDto> grooDailySumAmountDtoList = getGrooDailySumAmountDtoList();
		GrooMonthSumAmountDto grooMonthSumAmountDto = getExpectedMonthlySumCount(grooDailySumAmountDtoList);
		given(grooSavingRepository.getDailySumAndAmount(1L, 2023, 11)).willReturn(grooDailySumAmountDtoList);
		given(grooSavingRepository.getSumAndAmountByMonth(1L, 2023, 11)).willReturn(grooMonthSumAmountDto);

		//then
		GrooMonthDto grooMonthDto = grooSavingService.getDailySavingAmountByMonth(1L, 2023, 11);

		//
		verify(grooSavingRepository).getDailySumAndAmount(1L,2023,11);
		verify(grooSavingRepository).getSumAndAmountByMonth(1L, 2023, 11);
		assertThat(grooMonthDto.accusationCount()).isEqualTo(grooMonthSumAmountDto.getAccusationCount());
		assertThat(grooMonthDto.accusationSum()).isEqualTo(grooMonthSumAmountDto.getAccusationSum());
		assertThat(grooMonthDto.proofCount()).isEqualTo(grooMonthSumAmountDto.getProofCount());
		assertThat(grooMonthDto.proofSum()).isEqualTo(grooMonthSumAmountDto.getProofSum());
		assertThat(grooMonthDto.grooSavingList().size()).isEqualTo(grooDailySumAmountDtoList.size());
	}

	@Test
	@DisplayName("일주일간 일별 인증 횟수를 조회할 수 있다.")
	void getProofCountByWeek(){
		// given
		LocalDate today = LocalDate.now();
		LocalDate startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		LocalDate endDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
		List<WeeklyProofCountDto> expectedWeeklyProofCountDtoList = getWeeklyProofCountDtoList(startDate);
		given(grooSavingRepository.getDailyProofCount(1L, startDate, endDate)).willReturn(expectedWeeklyProofCountDtoList);

		// when
		List<GrooDailyDto> weeklyProofCountDtoList = grooSavingService.getDailyProofCountByWeek(1L);

		// then
		verify(grooSavingRepository).getDailyProofCount(1L, startDate, endDate);
		assertThat(weeklyProofCountDtoList).hasSize(7);
	}

	public List<WeeklyProofCountDto> getWeeklyProofCountDtoList(LocalDate startDate) {
		List<WeeklyProofCountDto> weeklyProofCountDtoList = new ArrayList<>();
		for (int i = 0; i<3; i++){
			weeklyProofCountDtoList.add(
				new WeeklyProofCountDto(Date.valueOf(startDate.plusDays(i)), new Random().nextLong(10L)));
		}
		return weeklyProofCountDtoList;
	}

	public List<GrooDailySumAmountDto> getGrooDailySumAmountDtoList() {
		List<GrooDailySumAmountDto> grooDailySumAmountDtos = new ArrayList<>();
		for (int i =0;i<30;i++){
			int randomNum = new Random().nextInt(50);
			long sum1 = new Random().nextLong(1000L);
			long sum2 = new Random().nextLong(1000L);
			long count1 = new Random().nextLong(10L);
			long count2 = new Random().nextLong(10L);
			LocalDate localDate = LocalDate.of(2023, 11, 5).plusDays(randomNum);
			Date date = Date.valueOf(String.valueOf(localDate));
			grooDailySumAmountDtos.add(new GrooDailySumAmountDto(date, sum1, count1, sum2, count2));
		}
		return grooDailySumAmountDtos;
	}

	public GrooMonthSumAmountDto getExpectedMonthlySumCount(List<GrooDailySumAmountDto> grooDailySumAmountDtos) {
		long expectedProofSum = 0L;
		long expectedProofCount = 0L;
		long expectedAccusationSum = 0L;
		long expectedAccusationCount = 0L;

		for (var dailySumAmountDto:grooDailySumAmountDtos){
			if (dailySumAmountDto.getDate().toLocalDate().getYear() == 2023 && dailySumAmountDto.getDate().toLocalDate().getMonthValue() == 11) {
				expectedAccusationSum += dailySumAmountDto.getAccusationSum();
				expectedAccusationCount += dailySumAmountDto.getAccusationCount();
				expectedProofSum += dailySumAmountDto.getProofSum();
				expectedProofCount += dailySumAmountDto.getProofCount();
			}
		}

		return new GrooMonthSumAmountDto(expectedProofSum, expectedProofCount,
			expectedAccusationSum, expectedAccusationCount);
	}

}
