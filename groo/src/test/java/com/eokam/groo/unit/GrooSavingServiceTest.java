package com.eokam.groo.unit;

import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.application.service.GrooSavingService;
import com.eokam.groo.application.service.GrooSavingServiceImpl;
import com.eokam.groo.domain.entity.GrooSaving;
import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;
import com.eokam.groo.infrastructure.repository.GrooSavingRepository;

@ActiveProfiles("test")
public class GrooSavingServiceTest {

	@Mock
	private GrooSavingRepository grooSavingRepository;

	@InjectMocks
	private GrooSavingServiceImpl grooSavingService;

	@BeforeEach
	public void setUp() throws Exception {
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
		BDDMockito.given(grooSavingRepository.save(any())).willReturn(grooSaving);

		// when
		GrooSavingDto getGrooSavingDto = grooSavingService.createGrooSaving(grooSavingDto);

		// then
		BDDMockito.verify(grooSavingRepository).save(any());
		Assertions.assertThat(getGrooSavingDto.savedAt()).isEqualTo(grooSavingDto.savedAt());
	}
}
