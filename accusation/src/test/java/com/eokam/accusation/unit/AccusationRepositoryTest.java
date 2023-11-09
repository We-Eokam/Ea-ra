package com.eokam.accusation.unit;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.domain.entity.AccusationImage;
import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.infrastructure.repository.AccusationImageRepository;
import com.eokam.accusation.infrastructure.repository.AccusationRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
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
		assertThat(savedAccusation.getWitnessId()).isEqualTo(1L);
		assertThat(savedAccusation.getMemberId()).isEqualTo(2L);
		assertThat(savedAccusation.getActivityType()).isEqualTo(ActivityType.FOOD);

		assertThat(savedAccusationImage.getAccusation()).isEqualTo(accusation);
		assertThat(savedAccusationImage.getFileUrl()).isEqualTo("fileURL");
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
		Page<Accusation> pageAccusation = accusationRepository.findByMemberId(memberId, PageRequest.of(0, 12));

		// then
		assertThat(pageAccusation.getContent()).hasSize(accusationNumber);
		for (Accusation accusation : pageAccusation.getContent()) {
			List<AccusationImage> findAccusationImage = accusationImageRepository.findByAccusation_AccusationId(
				accusation.getAccusationId());
			assertThat(findAccusationImage).hasSize(1);
			assertThat(findAccusationImage.get(0).getFileUrl()).isEqualTo("fileURL");
		}
	}

	@Test
	@DisplayName("특정 고발장의 상세 내용을 조회한다.")
	void getAccusationDetail() {
		// given
		Accusation accusation = Accusation.builder().witnessId(1L).memberId(2L).activityType(ActivityType.FOOD).build();
		AccusationImage accusationImage = AccusationImage.builder().accusation(accusation).fileUrl("fileURL").build();
		AccusationImage accusationImage2 = AccusationImage.builder().accusation(accusation).fileUrl("fileURL2").build();
		accusationRepository.save(accusation);
		accusationImageRepository.save(accusationImage);
		accusationImageRepository.save(accusationImage2);

		// when
		Optional<Accusation> findAccusation = accusationRepository.findByAccusationId(accusation.getAccusationId());
		List<AccusationImage> findAccusationImages = accusationImageRepository.findByAccusation_AccusationId(
			accusation.getAccusationId());

		// then
		assertThat(findAccusation).isPresent();
		assertThat(findAccusation.get().getAccusationId()).isEqualTo(accusation.getAccusationId());
		assertThat(findAccusation.get().getWitnessId()).isEqualTo(accusation.getWitnessId());
		assertThat(findAccusation.get().getMemberId()).isEqualTo(accusation.getMemberId());
		assertThat(findAccusation.get().getActivityType()).isEqualTo(ActivityType.FOOD);
		assertThat(findAccusationImages).hasSize(2);
		assertThat(findAccusationImages.get(0).getAccusation().getAccusationId())
			.isEqualTo(findAccusation.get().getAccusationId());
		assertThat(findAccusationImages.get(0).getFileUrl()).isEqualTo("fileURL");
		assertThat(findAccusationImages.get(1).getAccusation().getAccusationId())
			.isEqualTo(findAccusation.get().getAccusationId());
		assertThat(findAccusationImages.get(1).getFileUrl()).isEqualTo("fileURL2");
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
