package com.eokam.proof.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

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
		String testJwt = createJwt();

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

	private String createJwt() {
		long memberId = 1L;
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
			.contents("null")
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
			.contents("null")
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
			.contents("null")
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
			.contents("null")
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
			.contents("null")
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