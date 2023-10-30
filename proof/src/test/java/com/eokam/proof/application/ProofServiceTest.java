package com.eokam.proof.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.eokam.proof.common.BaseServiceTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofRepository;

class ProofServiceTest extends BaseServiceTest {

	@InjectMocks
	ProofService proofService;

	@Mock
	ProofRepository proofRepository;

	@Test
	@DisplayName("내 인증 내역 조회를 성공")
	void getMyProofListSuccess() {
		LongStream.range(1, 6).forEach(this::generateProof);

		long memberId = 1L;
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		// given
		String testJwt = "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";

		given(proofRepository.findAllByMemberId(1L)).willReturn(EXPECTED_MY_PROOF_LIST());

		// when
		List<Proof> actualResponse = proofService.getMyProofList(testJwt);

		// then
		assertThat(actualResponse).usingRecursiveAssertion().isEqualTo(EXPECTED_MY_PROOF_LIST());
	}

	private void generateProof(Long i) {
		proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.proofImages(TEST_IMAGE)
			.build());

		proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.cCompanyId(2L)
			.proofImages(TEST_IMAGE)
			.build());

		proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.MULTI_USE_CONTAINER)
			.cCompanyId(3L)
			.proofImages(TEST_IMAGE)
			.build());

		proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.TUMBLER)
			.cCompanyId(4L)
			.proofImages(TEST_IMAGE)
			.build());

		proofRepository.save(Proof.builder()
			.memberId(1L)
			.activityType(ActivityType.EMISSION_FREE_CAR)
			.cCompanyId(5L)
			.proofImages(TEST_IMAGE)
			.build());
	}

	private static final List<ProofImage> TEST_IMAGE = List.of(
		ProofImage.builder()
			.fileUrl("http://test")
			.fileName("test.jpg")
			.build()
	);

	private static List<Proof> EXPECTED_MY_PROOF_LIST() {
		List<Proof> proofList = new ArrayList<>();

		proofList.add(
			Proof.builder()
				.memberId(1L)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.cCompanyId(1L)
				.proofImages(TEST_IMAGE)
				.build());
		proofList.add(
			Proof.builder()
				.memberId(1L)
				.activityType(ActivityType.DISPOSABLE_CUP)
				.cCompanyId(2L)
				.proofImages(TEST_IMAGE)
				.build());
		proofList.add(
			Proof.builder()
				.memberId(1L)
				.activityType(ActivityType.MULTI_USE_CONTAINER)
				.cCompanyId(3L)
				.proofImages(TEST_IMAGE)
				.build());
		proofList.add(
			Proof.builder()
				.memberId(1L)
				.activityType(ActivityType.TUMBLER)
				.cCompanyId(4L)
				.proofImages(TEST_IMAGE)
				.build());
		proofList.add(
			Proof.builder()
				.memberId(1L)
				.activityType(ActivityType.EMISSION_FREE_CAR)
				.cCompanyId(5L)
				.proofImages(TEST_IMAGE)
				.build());

		return proofList;
	}

}