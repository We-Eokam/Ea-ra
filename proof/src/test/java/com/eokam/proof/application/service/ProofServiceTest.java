package com.eokam.proof.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.proof.application.dto.ProofCreateDto;
import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.common.BaseServiceTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofImageRepository;
import com.eokam.proof.domain.repository.ProofRepository;

class ProofServiceTest extends BaseServiceTest {

	@InjectMocks
	ProofService proofService;

	@Mock
	ProofRepository proofRepository;

	@Mock
	ProofImageRepository proofImageRepository;

	private static final List<Proof> EXPECTED_MY_PROOF_LIST = new ArrayList<>();

	@BeforeEach
	void resetRepository() {
		proofRepository.deleteAll();
		proofImageRepository.deleteAll();
	}

	@Test
	@DisplayName("내 인증 내역 조회를 성공")
	@Transactional
	void getMyProofList_Success() {
		LongStream.range(1, 6).forEach(this::generateProof);

		// given
		String testJwt = createJwt(1L);

		PageRequest pageRequest = PageRequest.of(0, 5);

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), EXPECTED_MY_PROOF_LIST.size());

		Page<Proof> proofPage = new PageImpl<>(EXPECTED_MY_PROOF_LIST.subList(start, end), pageRequest,
			EXPECTED_MY_PROOF_LIST.size());

		given(proofRepository.findAllByMemberId(anyLong(), any(PageRequest.class)))
			.willReturn(proofPage);

		// when
		Page<ProofDto> actualResponse = proofService.getMyProofList(testJwt, pageRequest);

		// then
		assertThat(actualResponse).usingRecursiveAssertion().isEqualTo(ProofDto.toDtoPage(proofPage));
	}

	@Test
	@DisplayName("인증 생성을 성공")
	@Disabled
	@Transactional
	void createProof_Success() throws IOException {
		// given
		ClassPathResource resource = new ClassPathResource("static/earth.jpg");

		final String EXPECTED_FILE_NAME = "test";
		final String EXPECTED_ORIGINAL_NAME = "test.jpg";
		final Long EXPECTED_MEMBER_ID = 1L;
		final Long EXPECTED_CCOMPANY_ID = 1L;
		final Long EXPECTED_PROOF_ID = 1L;
		final LocalDateTime EXPECTED_CREATED_AT = LocalDateTime.now();
		final String EXPECTED_FILE_URL = "http://test.com";
		final Long EXPECTED_PROOF_IMAGE_ID = 1L;

		MockMultipartFile file =
			new MockMultipartFile(
				EXPECTED_FILE_NAME,
				EXPECTED_ORIGINAL_NAME,
				MediaType.IMAGE_JPEG_VALUE,
				resource.getContentAsByteArray()
			);

		ActivityType EXPECTED_ACTIVITY_TYPE = ActivityType.ELECTRONIC_RECEIPT;
		ProofCreateDto proofCreateDto = ProofCreateDto.builder()
			.memberId(EXPECTED_MEMBER_ID)
			.cCompanyId(EXPECTED_CCOMPANY_ID)
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.content(null)
			.build();

		List<MultipartFile> multipartFiles = new ArrayList<>();
		multipartFiles.add(file);

		Proof proof = Proof.builder()
			.proofId(EXPECTED_PROOF_ID)
			.memberId(EXPECTED_MEMBER_ID)
			.cCompanyId(EXPECTED_CCOMPANY_ID)
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.createdAt(EXPECTED_CREATED_AT)
			.build();

		ProofImage proofImage = ProofImage.builder()
			.proofImageId(EXPECTED_PROOF_IMAGE_ID)
			.fileName(EXPECTED_FILE_NAME)
			.fileUrl(EXPECTED_FILE_URL)
			.proof(proof)
			.build();

		given(proofRepository.save(any(Proof.class))).willReturn(proof);
		given(proofImageRepository.save(any(ProofImage.class))).willReturn(proofImage);

		// when
		ProofDto actualResponse = proofService.createProof(proofCreateDto, multipartFiles);

		// then
		assertThat(actualResponse.proofId()).isEqualTo(EXPECTED_PROOF_ID);
		assertThat(actualResponse.memberId()).isEqualTo(EXPECTED_MEMBER_ID);
		assertThat(actualResponse.cCompanyId()).isEqualTo(EXPECTED_CCOMPANY_ID);
		assertThat(actualResponse.activityType()).isEqualTo(EXPECTED_ACTIVITY_TYPE);
		assertThat(actualResponse.createdAt()).isEqualTo(EXPECTED_CREATED_AT);
		assertThat(actualResponse.content()).isBlank();
		assertThat(actualResponse.proofImages().get(0).proofImageId()).isEqualTo(EXPECTED_PROOF_IMAGE_ID);
		assertThat(actualResponse.proofImages().get(0).fileName()).isEqualTo(EXPECTED_FILE_NAME);
		assertThat(actualResponse.proofImages().get(0).fileUrl()).isEqualTo(EXPECTED_FILE_URL);
	}

	@Test
	@DisplayName("기타 인증 생성을 성공")
	@Disabled
	@Transactional
	void createEtcProof_Success() throws IOException {
		ClassPathResource resource = new ClassPathResource("static/earth.jpg");

		String EXPECTED_FILE_NAME = "test";
		String EXPECTED_ORIGINAL_NAME = "test.jpg";
		String EXPECT_CONTENT = "플로깅을 했어요!";
		Long EXPECTED_PROOF_ID = 1L;
		Long EXPECTED_MEMBER_ID = 1L;
		Long EXPECTED_PROOF_IMAGE_ID = 1L;
		ActivityType EXPECTED_ACTIVITY_TYPE = ActivityType.ETC;
		String EXPECTED_FILE_URL = "http://test.com";

		MockMultipartFile file =
			new MockMultipartFile(
				EXPECTED_FILE_NAME,
				EXPECTED_ORIGINAL_NAME,
				MediaType.IMAGE_JPEG_VALUE,
				resource.getContentAsByteArray()
			);

		ProofCreateDto proofCreateDto = ProofCreateDto.builder()
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.memberId(EXPECTED_MEMBER_ID)
			.content(EXPECT_CONTENT)
			.build();

		List<MultipartFile> multipartFiles = new ArrayList<>();
		multipartFiles.add(file);

		Proof proof = Proof.builder()
			.proofId(EXPECTED_PROOF_ID)
			.memberId(EXPECTED_MEMBER_ID)
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.createdAt(LocalDateTime.now())
			.contents(EXPECT_CONTENT)
			.build();

		ProofImage proofImage = ProofImage.builder()
			.proofImageId(EXPECTED_PROOF_IMAGE_ID)
			.fileName(EXPECTED_FILE_NAME)
			.fileUrl(EXPECTED_FILE_URL)
			.proof(proof)
			.build();

		given(proofRepository.save(any(Proof.class))).willReturn(proof);
		given(proofImageRepository.save(any(ProofImage.class))).willReturn(proofImage);

		// when
		ProofDto actualResponse = proofService.createProof(proofCreateDto, multipartFiles);

		// then
		assertThat(actualResponse.proofId()).isEqualTo(EXPECTED_PROOF_ID);
		assertThat(actualResponse.memberId()).isEqualTo(EXPECTED_MEMBER_ID);
		assertThat(actualResponse.cCompanyId()).isNull();
		assertThat(actualResponse.activityType()).isEqualTo(EXPECTED_ACTIVITY_TYPE);
		assertThat(actualResponse.createdAt()).isEqualTo(proof.getCreatedAt());
		assertThat(actualResponse.proofImages().get(0).proofImageId()).isEqualTo(EXPECTED_PROOF_IMAGE_ID);
		assertThat(actualResponse.proofImages().get(0).fileName()).isEqualTo(EXPECTED_FILE_NAME);
		assertThat(actualResponse.proofImages().get(0).fileUrl()).isEqualTo(EXPECTED_FILE_URL);
	}

	private String createJwt(Long memberId) {
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		return "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";
	}

	private void generateProof(Long i) {
		List<ProofImage> proofImages1 = new ArrayList<>();
		proofImages1.add(ProofImage.builder()
			.fileName("test1.jpg")
			.fileUrl("http://test1.com")
			.build());

		Proof proof1 = Proof.builder()
			.proofId(5 * (i - 1) + 1)
			.memberId(i)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.proofImages(proofImages1)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImage> proofImages2 = new ArrayList<>();
		proofImages2.add(ProofImage.builder()
			.fileName("test2.jpg")
			.fileUrl("http://test2.com")
			.build());

		Proof proof2 = Proof.builder()
			.proofId(5 * (i - 1) + 2)
			.memberId(i)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.cCompanyId(2L)
			.proofImages(proofImages2)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImage> proofImages3 = new ArrayList<>();
		proofImages3.add(ProofImage.builder()
			.fileName("test3.jpg")
			.fileUrl("http://test3.com")
			.build());

		Proof proof3 = Proof.builder()
			.proofId(5 * (i - 1) + 3)
			.memberId(i)
			.activityType(ActivityType.MULTI_USE_CONTAINER)
			.cCompanyId(3L)
			.proofImages(proofImages3)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImage> proofImages4 = new ArrayList<>();
		proofImages4.add(ProofImage.builder()
			.fileName("test4.jpg")
			.fileUrl("http://test4.com")
			.build());

		Proof proof4 = Proof.builder()
			.proofId(5 * (i - 1) + 4)
			.memberId(i)
			.activityType(ActivityType.TUMBLER)
			.cCompanyId(4L)
			.proofImages(proofImages4)
			.createdAt(LocalDateTime.now())
			.build();

		List<ProofImage> proofImages5 = new ArrayList<>();
		proofImages5.add(ProofImage.builder()
			.fileName("test5.jpg")
			.fileUrl("http://test5.com")
			.build());

		Proof proof5 = Proof.builder()
			.proofId(5 * (i - 1) + 5)
			.memberId(i)
			.activityType(ActivityType.EMISSION_FREE_CAR)
			.cCompanyId(5L)
			.proofImages(proofImages4)
			.createdAt(LocalDateTime.now())
			.build();

		if (i == 1) {
			EXPECTED_MY_PROOF_LIST.add(proof1);
			EXPECTED_MY_PROOF_LIST.add(proof2);
			EXPECTED_MY_PROOF_LIST.add(proof3);
			EXPECTED_MY_PROOF_LIST.add(proof4);
			EXPECTED_MY_PROOF_LIST.add(proof5);
		}

	}

}