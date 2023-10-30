package com.eokam.proof.application;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eokam.proof.common.BaseServiceTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;
import com.eokam.proof.domain.repository.ProofImageRepository;
import com.eokam.proof.domain.repository.ProofRepository;

class ProofServiceTest extends BaseServiceTest {

	@Autowired
	ProofService proofService;

	@Autowired
	ProofRepository proofRepository;

	@Autowired
	ProofImageRepository proofImageRepository;

	private static final List<Proof> EXPECTED_MY_PROOF_LIST = new ArrayList<>();

	@Test
	@DisplayName("내 인증 내역 조회를 성공")
	@Transactional
	void getMyProofListSuccess() {
		LongStream.range(1, 6).forEach(this::generateProof);

		long memberId = 1L;
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		// given
		String testJwt = "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";

		// when
		List<Proof> actualResponse = proofService.getMyProofList(testJwt);

		// then
		assertThat(actualResponse).usingRecursiveAssertion().isEqualTo(EXPECTED_MY_PROOF_LIST);
	}

	private void generateProof(Long i) {
		Proof proof1 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test1.jpg")
			.fileUrl("http://test1.com")
			.proof(proof1)
			.build());

		Proof proof2 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.DISPOSABLE_CUP)
			.cCompanyId(2L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test2.jpg")
			.fileUrl("http://test2.com")
			.proof(proof2)
			.build());

		Proof proof3 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.MULTI_USE_CONTAINER)
			.cCompanyId(3L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test3.jpg")
			.fileUrl("http://test3.com")
			.proof(proof3)
			.build());

		Proof proof4 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.TUMBLER)
			.cCompanyId(4L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test4.jpg")
			.fileUrl("http://test4.com")
			.proof(proof4)
			.build());

		Proof proof5 = proofRepository.save(Proof.builder()
			.memberId(i)
			.activityType(ActivityType.EMISSION_FREE_CAR)
			.cCompanyId(5L)
			.build());

		proofImageRepository.save(ProofImage.builder()
			.fileName("test5.jpg")
			.fileUrl("http://test5.com")
			.proof(proof5)
			.build());

		if (i == 1) {
			EXPECTED_MY_PROOF_LIST.add(proof1);
			EXPECTED_MY_PROOF_LIST.add(proof2);
			EXPECTED_MY_PROOF_LIST.add(proof3);
			EXPECTED_MY_PROOF_LIST.add(proof4);
			EXPECTED_MY_PROOF_LIST.add(proof5);
		}
	}

}