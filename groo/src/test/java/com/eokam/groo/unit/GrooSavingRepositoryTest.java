package com.eokam.groo.unit;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

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
import com.eokam.groo.infrastructure.repository.GrooSavingRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
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
		assertThat(savedGrooSaving.getSavingId()).isEqualTo(1L);
		assertThat(savedGrooSaving.getMemberId()).isEqualTo(1L);
		assertThat(savedGrooSaving.getSavingType()).isEqualTo(SavingType.PROOF);
		assertThat(savedGrooSaving.getActivityType()).isEqualTo(ActivityType.DISPOSABLE_CUP);
		assertThat(savedGrooSaving.getAmount()).isEqualTo(ActivityType.DISPOSABLE_CUP.getSavingAmount());
		assertThat(savedGrooSaving.getRemainGroo()).isEqualTo(100L);
		assertThat(savedGrooSaving.getProofAccusationId()).isEqualTo(2L);
	}
}
