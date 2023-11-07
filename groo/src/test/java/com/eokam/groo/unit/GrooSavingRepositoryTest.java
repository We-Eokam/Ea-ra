package com.eokam.groo.unit;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.infrastructure.dto.GrooDailySumAmountDto;
import com.eokam.groo.infrastructure.dto.GrooMonthSumAmountDto;
import com.eokam.groo.infrastructure.repository.GrooSavingRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GrooSavingRepositoryTest {

	@Autowired
	private GrooSavingRepository grooSavingRepository;

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
	@DisplayName("특정 월의 그린 적립 내역을 조회할 수 있다.")
	@Transactional
	void getDailySavingAmountsByMonth() {
		long expectedProofSum = 0;
		long expectProofCount = 0;
		long expectedAccusationSum = 0;
		long expectedAccusationCount = 0;

		List<GrooSaving> grooSavings = new ArrayList<>();
		for (int i=0; i<10; i++){
			int randomNum = new Random().nextInt(50);
			long memberId = new Random().nextLong(2L);
			LocalDateTime localDateTime = LocalDateTime.now().plusDays(randomNum);
			GrooSaving grooSaving = GrooSaving.builder()
				.memberId(memberId)
				.savingType(SavingType.PROOF)
				.activityType(ActivityType.DISPOSABLE_CUP)
				.amount(ActivityType.DISPOSABLE_CUP.getSavingAmount())
				.savedAt(localDateTime)
				.proofAccusationId(2L)
				.remainGroo(100L)
				.build();
			grooSavings.add(grooSaving);
			if (memberId == 1L && localDateTime.getMonth().equals(Month.NOVEMBER)){
				expectedProofSum += ActivityType.DISPOSABLE_CUP.getSavingAmount();
				expectProofCount++;
			}
		}
		for (int i=0; i<10; i++){
			int randomNum = new Random().nextInt(50);
			long memberId = new Random().nextLong(2L);
			LocalDateTime localDateTime = LocalDateTime.now().plusDays(randomNum);
			GrooSaving grooSaving = GrooSaving.builder()
				.memberId(memberId)
				.savingType(SavingType.ACCUSATION)
				.activityType(ActivityType.FOOD)
				.amount(ActivityType.FOOD.getSavingAmount())
				.savedAt(localDateTime)
				.proofAccusationId(2L)
				.remainGroo(100L)
				.build();
			grooSavings.add(grooSaving);
			if (memberId == 1L && localDateTime.getMonth().equals(Month.NOVEMBER)){
				expectedAccusationSum += ActivityType.FOOD.getSavingAmount();
				expectedAccusationCount++;
			}
		}

		// when
		grooSavingRepository.saveAll(grooSavings);

		// then
		GrooMonthSumAmountDto sumAndAmountByMonth = grooSavingRepository.getSumAndAmountByMonth(1L, 2023, 11);

		assertThat(sumAndAmountByMonth.getProofSum()).isEqualTo(expectedProofSum);
		assertThat(sumAndAmountByMonth.getProofCount()).isEqualTo(expectProofCount);
		assertThat(sumAndAmountByMonth.getAccusationSum()).isEqualTo(expectedAccusationSum);
		assertThat(sumAndAmountByMonth.getAccusationCount()).isEqualTo(expectedAccusationCount);

		long proofSum = 0;
		long proofCount = 0;
		long accusationSum = 0;
		long accusationCount = 0;

		List<GrooDailySumAmountDto> dailySumAndAmount = grooSavingRepository.getDailySumAndAmount(1L, 2023, 11);

		for (GrooDailySumAmountDto grooDailySumAmountDto:dailySumAndAmount) {
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
}
