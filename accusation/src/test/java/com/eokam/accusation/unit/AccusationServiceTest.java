package com.eokam.accusation.unit;

import static org.mockito.ArgumentMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

		BDDMockito.given(s3Service.uploadFile(multipartFile)).willReturn(fileUrls);
		BDDMockito.given(
				accusationRepository.save(argThat(a -> a.getActivityDetail().equals("Test Detail"))))
			.willReturn(accusation);
		BDDMockito.given(
				accusationImageRepository.save(argThat(aI -> aI.getFileUrl().equals("FileURL"))))
			.willReturn(accusationImage);

		// when
		accusationService.createAccusation(accusationDto, multipartFile);

		// then
		BDDMockito.verify(accusationRepository)
			.save(argThat(a -> a.getActivityDetail().equals("Test Detail")));
		BDDMockito.verify(accusationImageRepository)
			.save((argThat(aI -> aI.getFileUrl().equals("FileURL"))));
	}
}
