package com.eokam.proof.presentation.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.application.dto.ProofImageDto;
import com.eokam.proof.domain.constant.ActivityType;

public class ProofControllerTestStatic {
	public static List<ProofDto> EXPECTED_ALL_PROOF_LIST() {
		List<ProofDto> EXPECTED_ALL_PROOF_LIST = new ArrayList<>();

		create(EXPECTED_ALL_PROOF_LIST, 1L, 5L);

		return EXPECTED_ALL_PROOF_LIST;
	}

	public static List<ProofDto> EXPECTED_MY_PROOF_LIST() {
		List<ProofDto> EXPECTED_MY_PROOF_LIST = new ArrayList<>();

		create(EXPECTED_MY_PROOF_LIST, 1L, 1L);

		return EXPECTED_MY_PROOF_LIST;
	}

	public static List<ProofDto> EXPECTED_FRIENDS_PROOF_LIST() {
		List<ProofDto> EXPECTED_FRIENDS_PROOF_LIST = new ArrayList<>();

		create(EXPECTED_FRIENDS_PROOF_LIST, 2L, 2L);

		return EXPECTED_FRIENDS_PROOF_LIST;
	}

	private static void create(List<ProofDto> EXPECTED_ALL_PROOF_LIST, Long start, Long end) {
		LongStream.range(start, end + 1).forEach(i -> {
			List<ProofImageDto> proofImages1 = new ArrayList<>();
			proofImages1.add(ProofImageDto.builder()
				.proofImageId(1L)
				.fileName("test1.jpg")
				.fileUrl("http://test1.com")
				.build());

			ProofDto proof1 = ProofDto.builder()
				.proofId(5 * (i - 1) + 1)
				.memberId(i)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.cCompanyId(1L)
				.proofImages(proofImages1)
				.createdAt(LocalDateTime.now()
					.minusDays(5 * (i - 1) + 1)
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
				.build();

			EXPECTED_ALL_PROOF_LIST.add(proof1);

			List<ProofImageDto> proofImages2 = new ArrayList<>();
			proofImages2.add(ProofImageDto.builder()
				.proofImageId(2L)
				.fileName("test2.jpg")
				.fileUrl("http://test2.com")
				.build());

			ProofDto proof2 = ProofDto.builder()
				.proofId(5 * (i - 1) + 2)
				.memberId(i)
				.activityType(ActivityType.DISPOSABLE_CUP)
				.cCompanyId(2L)
				.proofImages(proofImages2)
				.createdAt(LocalDateTime.now()
					.minusDays(5 * (i - 1) + 1)
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
				.build();

			EXPECTED_ALL_PROOF_LIST.add(proof2);

			List<ProofImageDto> proofImages3 = new ArrayList<>();
			proofImages3.add(ProofImageDto.builder()
				.proofImageId(3L)
				.fileName("test3.jpg")
				.fileUrl("http://test3.com")
				.build());

			ProofDto proof3 = ProofDto.builder()
				.proofId(5 * (i - 1) + 3)
				.memberId(i)
				.activityType(ActivityType.MULTI_USE_CONTAINER)
				.cCompanyId(3L)
				.proofImages(proofImages3)
				.createdAt(LocalDateTime.now()
					.minusDays(5 * (i - 1) + 1)
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
				.build();

			EXPECTED_ALL_PROOF_LIST.add(proof3);

			List<ProofImageDto> proofImages4 = new ArrayList<>();
			proofImages4.add(ProofImageDto.builder()
				.proofImageId(4L)
				.fileName("test4.jpg")
				.fileUrl("http://test4.com")
				.build());

			ProofDto proof4 = ProofDto.builder()
				.proofId(5 * (i - 1) + 4)
				.memberId(i)
				.activityType(ActivityType.TUMBLER)
				.cCompanyId(4L)
				.proofImages(proofImages4)
				.createdAt(LocalDateTime.now()
					.minusDays(5 * (i - 1) + 1)
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
				.build();

			EXPECTED_ALL_PROOF_LIST.add(proof4);

			List<ProofImageDto> proofImages5 = new ArrayList<>();
			proofImages5.add(ProofImageDto.builder()
				.proofImageId(5L)
				.fileName("test5.jpg")
				.fileUrl("http://test5.com")
				.build());

			ProofDto proof5 = ProofDto.builder()
				.proofId(5 * (i - 1) + 5)
				.memberId(i)
				.activityType(ActivityType.EMISSION_FREE_CAR)
				.cCompanyId(5L)
				.proofImages(proofImages5)
				.createdAt(LocalDateTime.now()
					.minusDays(5 * (i - 1) + 1)
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
				.build();

			EXPECTED_ALL_PROOF_LIST.add(proof5);
		});
	}

	// if (start == 1) {
	// 	EXPECTED_MY_PROOF_LIST.add(proof1);
	// 	EXPECTED_MY_PROOF_LIST.add(proof2);
	// 	EXPECTED_MY_PROOF_LIST.add(proof3);
	// 	EXPECTED_MY_PROOF_LIST.add(proof4);
	// 	EXPECTED_MY_PROOF_LIST.add(proof5);
	// }
	//
	// 	if (start == 2) {
	// 	EXPECTED_FRIENDS_PROOF_LIST.add(proof1);
	// 	EXPECTED_FRIENDS_PROOF_LIST.add(proof2);
	// 	EXPECTED_FRIENDS_PROOF_LIST.add(proof3);
	// 	EXPECTED_FRIENDS_PROOF_LIST.add(proof4);
	// 	EXPECTED_FRIENDS_PROOF_LIST.add(proof5);
	// }

}
