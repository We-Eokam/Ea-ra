package com.eokam.accusation.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.domain.entity.AccusationImage;
import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.infrastructure.repository.AccusationImageRepository;
import com.eokam.accusation.infrastructure.repository.AccusationRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccusationRepositoryTest {

	@Autowired
	private AccusationRepository accusationRepository;

	@Autowired
	private AccusationImageRepository accusationImageRepository;

	@Test
	@DisplayName("고발장을 저장한다.")
	@Transactional
	void createAccusation() {
		//given
		Accusation accusation = Accusation.builder().witnessId(1L).memberId(2L).activityType(ActivityType.FOOD).build();
		AccusationImage accusationImage = AccusationImage.builder().accusation(accusation).fileUrl("fileURL").build();

		//when
		Accusation savedAccusation = accusationRepository.save(accusation);
		AccusationImage savedAccusationImage = accusationImageRepository.save(accusationImage);

		// then
		Assertions.assertThat(savedAccusation.getWitnessId()).isEqualTo(1L);
		Assertions.assertThat(savedAccusation.getMemberId()).isEqualTo(2L);
		Assertions.assertThat(savedAccusation.getActivityType()).isEqualTo(ActivityType.FOOD);

		Assertions.assertThat(savedAccusationImage.getAccusation()).isEqualTo(accusation);
		Assertions.assertThat(savedAccusationImage.getFileUrl()).isEqualTo("fileURL");
	}

}
