package com.eokam.groo.unit;

import static com.eokam.groo.acceptance.GrooAcceptanceStep.*;
import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.infrastructure.repository.GrooSavingRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GrooSavingRepositoryTest {

	@Autowired
	private GrooSavingRepository grooSavingRepository;

	private static Long expectedAccusationSum;
	private static Long expectedAccusationCount;
	private static Long expectedProofSum;
	private static Long expectProofCount;

	private static int START_DAY;
	private static int END_DAY;


	@Test
	@DisplayName("그루 적립 내역을 저장한다.")
	@Transactional
	void saveGrooSaving() {
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

		// when
		GrooSaving savedGrooSaving = grooSavingRepository.save(grooSaving);

		// then
		assertThat(savedGrooSaving.getMemberId()).isEqualTo(1L);
		assertThat(savedGrooSaving.getSavingType()).isEqualTo(SavingType.PROOF);
		assertThat(savedGrooSaving.getActivityType()).isEqualTo(ActivityType.DISPOSABLE_CUP);
		assertThat(savedGrooSaving.getAmount()).isEqualTo(ActivityType.DISPOSABLE_CUP.getSavingAmount());
		assertThat(savedGrooSaving.getRemainGroo()).isEqualTo(100L);
		assertThat(savedGrooSaving.getProofAccusationId()).isEqualTo(2L);
	}

	@Test
	@DisplayName("특정 월의 일별 그린 적립 내역을 조회할 수 있다.")
	@Transactional
	void getDailySavingAmountsByMonth() {
		// given
		expectedProofSum = 0L;
		expectProofCount = 0L;
		expectedAccusationSum = 0L;
		expectedAccusationCount = 0L;

		List<GrooSaving> grooSavings = new ArrayList<>();
		for (int i=0; i<10; i++){
			GrooSaving grooSaving = of(SavingType.PROOF, ActivityType.DISPOSABLE_CUP);
			grooSavings.add(grooSaving);
		}
		for (int i=0; i<10; i++){
			GrooSaving grooSaving = of(SavingType.ACCUSATION, ActivityType.ELECTRICITY);
			grooSavings.add(grooSaving);
		}

		// when
		grooSavingRepository.saveAll(grooSavings);

		// then
		long proofSum = 0;
		long proofCount = 0;
		long accusationSum = 0;
		long accusationCount = 0;

		var dailySumAndAmountList = grooSavingRepository.getDailySumAndAmount(1L, 2023, 11);

		for (var grooDailySumAmountDto:dailySumAndAmountList) {
			proofSum += grooDailySumAmountDto.getProofSum();
			proofCount += grooDailySumAmountDto.getProofCount();
			accusationSum += grooDailySumAmountDto.getAccusationSum();
			accusationCount += grooDailySumAmountDto.getAccusationCount();
		}

		assertThat(proofSum).isEqualTo(expectedProofSum);
		assertThat(proofCount).isEqualTo(expectProofCount);
		assertThat(accusationSum).isEqualTo(expectedAccusationSum);
		assertThat(accusationCount).isEqualTo(expectedAccusationCount);
	}

	@Test
	@DisplayName("특정 월의 그린 적립 양과 횟수를 조회할 수 있다.")
	@Transactional
	void getSavingAmountandCountByMonth() {
		// given
		expectedProofSum = 0L;
		expectProofCount = 0L;
		expectedAccusationSum = 0L;
		expectedAccusationCount = 0L;

		List<GrooSaving> grooSavings = new ArrayList<>();
		for (int i=0; i<10; i++){
			GrooSaving grooSaving = of(SavingType.PROOF, ActivityType.DISPOSABLE_CUP);
			grooSavings.add(grooSaving);
		}
		for (int i=0; i<10; i++){
			GrooSaving grooSaving = of(SavingType.ACCUSATION, ActivityType.ELECTRICITY);
			grooSavings.add(grooSaving);
		}

		// when
		grooSavingRepository.saveAll(grooSavings);

		// then
		var sumAndAmountByMonth = grooSavingRepository.getSumAndAmountByMonth(1L, 2023, 11);

		assertThat(sumAndAmountByMonth.getProofSum()).isEqualTo(expectedProofSum);
		assertThat(sumAndAmountByMonth.getProofCount()).isEqualTo(expectProofCount);
		assertThat(sumAndAmountByMonth.getAccusationSum()).isEqualTo(expectedAccusationSum);
		assertThat(sumAndAmountByMonth.getAccusationCount()).isEqualTo(expectedAccusationCount);
	}

	@Test
	@DisplayName("특정 월의 그린 적립 양과 횟수를 조회할 수 있다.")
	@Transactional
	void getProofCountByWeek() {
		// given
		LocalDate today = LocalDate.now();
		LocalDate startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		LocalDate endDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
		START_DAY = startDate.getDayOfMonth();
		END_DAY = endDate.getDayOfMonth();

		List<GrooSaving> grooSavings = new ArrayList<>();
		for (int i=0; i<10; i++){
			GrooSaving grooSaving = ofProof();
			grooSavings.add(grooSaving);
		}

		// when
		grooSavingRepository.saveAll(grooSavings);

		// then
		var dailyProofCountList = grooSavingRepository.getDailyProofCount(1L, startDate, endDate);

		Map<LocalDate, Long> expectedDailyProofCount = getExpectedDailyProofCount(grooSavings);
		assertThat(dailyProofCountList.size()).isEqualTo(expectedDailyProofCount.size());
		for (var dailyProofCount:dailyProofCountList) {
			LocalDate key = dailyProofCount.getDate().toLocalDate();
			assertThat(dailyProofCount.getProofCount()).isEqualTo(expectedDailyProofCount.get(key));
		}
	}

	public GrooSaving of(SavingType savingType, ActivityType activityType) {
		int randomNum = new Random().nextInt(50);
		long memberId = new Random().nextLong(2L);
		LocalDateTime localDateTime = LocalDateTime.now().plusDays(randomNum);
		if (memberId == 1L && localDateTime.getMonth().equals(Month.NOVEMBER)){
			if (SavingType.PROOF.equals(savingType)){
				expectedProofSum += activityType.getSavingAmount();
				expectProofCount++;
			} else {
				expectedAccusationSum += activityType.getSavingAmount();
				expectedAccusationCount++;
			}
		}
		return GrooSaving.builder()
			.memberId(memberId)
			.savingType(savingType)
			.activityType(activityType)
			.amount(activityType.getSavingAmount())
			.savedAt(localDateTime)
			.proofAccusationId(2L)
			.remainGroo(100L)
			.build();
	}

	public GrooSaving ofProof() {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		int randomNum = new Random().nextInt(5);
		return GrooSaving.builder()
			.memberId(1L)
			.savingType(SavingType.PROOF)
			.activityType(ActivityType.ECO_FRIENDLY_PRODUCTS)
			.amount(ActivityType.ECO_FRIENDLY_PRODUCTS.getSavingAmount())
			.savedAt(startDate.plusDays(randomNum))
			.proofAccusationId(2L)
			.remainGroo(100L)
			.build();
	}

	public Map<LocalDate, Long> getExpectedDailyProofCount(List<GrooSaving> grooSavings) {
		Map<LocalDate, Long> dailyProofCount = new HashMap<>();
		for (GrooSaving grooSaving:grooSavings) {
			if (isDateInRange(grooSaving.getSavedAt().toLocalDate())){
				LocalDate localDate = grooSaving.getSavedAt().toLocalDate();
				dailyProofCount.put(localDate, dailyProofCount.getOrDefault(localDate, 0L) + 1L);
			}
		}
		return dailyProofCount;
	}

	private boolean isDateInRange(LocalDate localDateTime){
		if (localDateTime.getYear() != YEAR){
			return false;
		}
		if (localDateTime.getMonthValue() != MONTH) {
			return false;
		}
		if (localDateTime.getDayOfMonth() < START_DAY || localDateTime.getDayOfMonth() > END_DAY){
			return false;
		}
		return true;
	}
}
