package com.eokam.accusation.unit;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.domain.entity.AccusationImage;
import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.infrastructure.repository.AccusationImageRepository;
import com.eokam.accusation.infrastructure.repository.AccusationRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AccusationRepositoryTest {
	@PersistenceContext
	EntityManager em;

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

	@Test
	@DisplayName("받은 고발장 목록을 가져온다.")
	@Transactional
	void getAccusationList() {
		// given
		final Long memberId = 1L;
		final int accusationNumber = 5;

		List<Accusation> accusations = number수만큼_memberId에게_고발장_보내기(memberId, accusationNumber);
		List<AccusationImage> accusationImage = 보낸_고발장에_사진_한개씩_추가(accusations);
		accusationRepository.saveAll(accusations);
		accusationImageRepository.saveAll(accusationImage);

		// when
		List<Accusation> accusationList = accusationRepository.findByMemberId(memberId);

		// then
		Assertions.assertThat(accusationList).hasSize(accusationNumber);
		for (Accusation accusation : accusationList) {
			List<AccusationImage> findAccusationImage = accusationImageRepository.findByAccusation_AccusationId(
				accusation.getAccusationId());
			Assertions.assertThat(findAccusationImage).hasSize(1);
			Assertions.assertThat(findAccusationImage.get(0).getFileUrl()).isEqualTo("fileURL");
		}
	}

	public List<Accusation> number수만큼_memberId에게_고발장_보내기(Long memberId, int number) {
		List<Accusation> accusations = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			accusations.add(Accusation.builder()
				.witnessId((long)i)
				.memberId(memberId)
				.activityType(ActivityType.FOOD)
				.build());
		}
		return accusations;
	}

	public List<AccusationImage> 보낸_고발장에_사진_한개씩_추가(List<Accusation> accusations) {
		List<AccusationImage> accusationImages = new ArrayList<>();
		for (Accusation accusation : accusations) {
			accusationImages.add(AccusationImage.builder()
				.accusation(accusation)
				.fileUrl("fileURL")
				.build());
		}
		return accusationImages;
	}

}
