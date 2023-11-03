package com.eokam.accusation.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.application.service.AccusationServiceImpl;
import com.eokam.accusation.domain.entity.Accusation;
import com.eokam.accusation.domain.entity.AccusationImage;
import com.eokam.accusation.global.constant.ActivityType;
import com.eokam.accusation.infrastructure.repository.AccusationImageRepository;
import com.eokam.accusation.infrastructure.repository.AccusationRepository;
import com.eokam.accusation.infrastructure.service.S3Service;

@ActiveProfiles("test")
public class AccusationServiceTest {

	@InjectMocks
	private AccusationServiceImpl accusationService;

	@Mock
	private AccusationRepository accusationRepository;

	@Mock
	private AccusationImageRepository accusationImageRepository;

	@Mock
	private S3Service s3Service;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("고발장을 저장한다.")
	void createAccusattion() throws IOException {
		// given
		List<MultipartFile> multipartFile = new ArrayList<>();
		multipartFile.add(new MockMultipartFile("file", "filename.png", "image/png", "some xml".getBytes()));
		List<String> fileUrls = new ArrayList<>();
		fileUrls.add("FileURL");

		AccusationDto accusationDto = AccusationDto.builder()
			.witnessId(1L)
			.memberId(2L)
			.activityType(ActivityType.OTHER)
			.activityDetail("Test Detail")
			.build();

		Accusation accusation = Accusation.from(accusationDto);
		AccusationImage accusationImage = AccusationImage.builder().accusation(accusation).fileUrl("FileURL").build();

		given(s3Service.uploadFile(multipartFile)).willReturn(fileUrls);
		given(
			accusationRepository.save(argThat(a -> a.getActivityDetail().equals("Test Detail"))))
			.willReturn(accusation);
		given(
			accusationImageRepository.save(argThat(aI -> aI.getFileUrl().equals("FileURL"))))
			.willReturn(accusationImage);

		// when
		accusationService.createAccusation(accusationDto, multipartFile);

		// then
		verify(accusationRepository)
			.save(argThat(a -> a.getActivityDetail().equals("Test Detail")));
		verify(accusationImageRepository)
			.save((argThat(aI -> aI.getFileUrl().equals("FileURL"))));
	}

	@Test
	@DisplayName("받은 고발장 목록을 조회한다.")
	void getAccusationList() {
		// given
		List<Accusation> accusations = number수만큼_memberId에게_고발장_보내기(1L, 10);
		List<AccusationImage> accusationImages = 보낸_고발장에_사진_한개씩_추가(accusations);
		given(accusationRepository.findByMemberId(1L)).willReturn(accusations);
		for (int i = 1; i <= 10; i++) {
			given(accusationImageRepository.findByAccusation_AccusationId((long)i)).willReturn(
				Collections.singletonList(accusationImages.get(i - 1)));
		}

		// when
		List<AccusationDto> accusationDtoList = accusationService.getAccusationList(1L);

		// then
		verify(accusationRepository).findByMemberId(1L);
		for (int i = 1; i <= 10; i++) {
			verify(accusationImageRepository).findByAccusation_AccusationId((long)i);
		}
		verify(accusationImageRepository, Mockito.times(10))
			.findByAccusation_AccusationId(any());
		assertThat(accusationDtoList).hasSize(10);
		for (AccusationDto accusationDto : accusationDtoList) {
			assertThat(accusationDto.imageList()).hasSize(1);
		}
	}

	@Test
	@DisplayName("특정 고발장의 상세 내용을 조회한다.")
	void getAccusationDetail() {
		// given
		Accusation accusation = Accusation.builder()
			.accusationId(1L)
			.witnessId(2L)
			.memberId(3L)
			.activityType(ActivityType.FOOD)
			.build();
		AccusationImage accusationImage1 = AccusationImage.builder()
			.accusation(accusation)
			.fileUrl("fileURL1")
			.build();
		AccusationImage accusationImage2 = AccusationImage.builder()
			.accusation(accusation)
			.fileUrl("fileURL2")
			.build();
		List<AccusationImage> accusationImageList = new ArrayList<>();
		accusationImageList.add(accusationImage1);
		accusationImageList.add(accusationImage2);

		given(accusationRepository.findByAccusationId(1L)).willReturn(Optional.of(accusation));
		given(accusationImageRepository.findByAccusation_AccusationId(1L)).willReturn(accusationImageList);

		// when
		AccusationDto accusationDto = accusationService.getAccusationDetail(1L);

		// then
		verify(accusationRepository).findByAccusationId(1L);
		verify(accusationImageRepository).findByAccusation_AccusationId(1L);
		assertThat(accusationDto.imageList()).hasSize(2);
		assertThat(accusationDto.imageList().get(0)).isEqualTo("fileURL1");
		assertThat(accusationDto.imageList().get(1)).isEqualTo("fileURL2");
	}

	public List<Accusation> number수만큼_memberId에게_고발장_보내기(Long memberId, int number) {
		List<Accusation> accusations = new ArrayList<>();
		for (int i = 1; i <= number; i++) {
			accusations.add(Accusation.builder()
				.accusationId((long)i)
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
