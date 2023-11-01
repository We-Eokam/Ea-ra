package com.eokam.proof.domain.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.eokam.proof.common.BaseRepositoryTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.domain.entity.Proof;
import com.eokam.proof.domain.entity.ProofImage;

class ProofRepositoryTest extends BaseRepositoryTest {
	@Autowired
	private ProofRepository proofRepository;
	@Autowired
	private ProofImageRepository proofImageRepository;

	@Test
	@DisplayName("나의 인증 내역 조회 테스트")
	void findAllByMemberId() {
		// given
		LongStream.range(1, 6).forEach(i -> {
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
		});

		Page<Proof> proofPage = proofRepository.findAllByMemberId(1L, PageRequest.of(0, 5));

		IntStream.range(0, 5).forEach(i -> {
			assertThat(proofPage.getSize()).isEqualTo(5);
			assertThat(proofPage.getContent().get(i).getMemberId()).isEqualTo(1L);
			assertThat(proofPage.getContent().get(i).getCCompanyId()).isEqualTo(i + 1);
			assertTrue(Hibernate.isInitialized(proofPage.getContent().get(i).getProofImages()));
			assertThat(proofPage.getContent().get(i).getContents()).isNullOrEmpty();
		});
		assertThat(proofPage.getContent().get(0).getActivityType()).isEqualTo(ActivityType.ELECTRONIC_RECEIPT);
		assertThat(proofPage.getContent().get(1).getActivityType()).isEqualTo(ActivityType.DISPOSABLE_CUP);
		assertThat(proofPage.getContent().get(2).getActivityType()).isEqualTo(ActivityType.MULTI_USE_CONTAINER);
		assertThat(proofPage.getContent().get(3).getActivityType()).isEqualTo(ActivityType.TUMBLER);
		assertThat(proofPage.getContent().get(4).getActivityType()).isEqualTo(ActivityType.EMISSION_FREE_CAR);
	}

	@Test
	@DisplayName("인증을 생성하는 테스트")
	void createProof() {
		// given
		Proof proof = Proof.builder()
			.memberId(1L)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.cCompanyId(1L)
			.build();

		ProofImage proofImage = ProofImage.builder()
			.fileName("test")
			.fileUrl("http://www.test.com")
			.proof(proof)
			.build();

		// when
		Proof savedProof = proofRepository.save(proof);
		ProofImage savedProofImage = proofImageRepository.save(proofImage);

		// then
		assertThat(savedProof.getMemberId()).isEqualTo(proof.getMemberId());
		assertThat(savedProof.getActivityType()).isEqualTo(proof.getActivityType());
		assertThat(savedProof.getCCompanyId()).isEqualTo(proof.getCCompanyId());
		assertThat(savedProof.getContents()).isNullOrEmpty();
		assertTrue(Hibernate.isInitialized(savedProof.getProofImages()));

		assertThat(savedProofImage.getFileName()).isEqualTo(proofImage.getFileName());
		assertThat(savedProofImage.getFileUrl()).isEqualTo(proofImage.getFileUrl());
		assertThat(savedProofImage.getProof()).isEqualTo(proofImage.getProof());
	}
}