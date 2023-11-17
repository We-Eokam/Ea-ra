package com.eokam.cpoint.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.Cpoint;
import com.eokam.cpoint.infra.common.CommonRepositoryTest;

public class CPointRepositoryTest extends CommonRepositoryTest {

	@Nested
	class sumCpointByMemberIdWhereCreatedAtInThisMonth_성공_테스트 {

		@Nested
		class 한개이하의_적립내역 {
			@Test
			public void 적립내역이_없는경우_포인트조회_테스트() {
				//given
				var 멤버A_ID = 1L;

				//when
				Integer 적립_포인트 = cpointRepository.sumCpointByMemberIdWhereCreatedAtInThisMonth(멤버A_ID);

				//then
				assertThat(적립_포인트).isEqualTo(0);
			}

			@Test
			public void 적립내역이_있는경우_포인트조회_테스트() {
				//given
				var 멤버A_ID = 1L;
				var 회사A = Company.builder().name("A").build();

				단일회사_생성(회사A);

				var 탄소중립포인트적립내역A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(100)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.company(회사A)
					.build();

				단일탄소중립포인트적립내역_생성(탄소중립포인트적립내역A);

				//when
				Integer 적립_포인트 = cpointRepository.sumCpointByMemberIdWhereCreatedAtInThisMonth(멤버A_ID);

				//then
				assertThat(적립_포인트).isEqualTo(100);
			}
		}

		@Nested
		class 두개이상의_적립내역 {
			@Nested
			class 같은달의_적립내역들만 {
				@Test
				public void 여러개의_적립내역이_있는경우_포인트조회_테스트() {
					//given
					var 멤버A_ID = 1L;
					var 멤버B_ID = 2L;

					var 회사A = Company.builder().name("A").build();
					var 회사B = Company.builder().name("B").build();

					여러회사_생성(회사A, 회사B);

					var 탄소중립포인트적립내역A_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(100)
						.activityType(ActivityType.ELECTRONIC_RECEIPT)
						.company(회사A)
						.build();

					var 탄소중립포인트적립내역B_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(1000)
						.activityType(ActivityType.REFILL_STATION)
						.company(회사B)
						.build();

					var 탄소중립포인트적립내역C_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(300)
						.activityType(ActivityType.DISPOSABLE_CUP)
						.company(회사A)
						.build();

					var 탄소중립포인트적립내역A_멤버B = Cpoint.builder()
						.memberId(멤버B_ID)
						.point(1000)
						.activityType(ActivityType.REFILL_STATION)
						.company(회사A)
						.build();

					여러탄소중립포인트적립내역_생성(
						탄소중립포인트적립내역A_멤버A,
						탄소중립포인트적립내역B_멤버A,
						탄소중립포인트적립내역C_멤버A,
						탄소중립포인트적립내역A_멤버B
					);

					//when
					Integer 결과_포인트 = cpointRepository.sumCpointByMemberIdWhereCreatedAtInThisMonth(멤버A_ID);

					//then
					assertThat(결과_포인트).isEqualTo(
						탄소중립포인트적립내역A_멤버A.getPoint()
							+ 탄소중립포인트적립내역B_멤버A.getPoint()
							+ 탄소중립포인트적립내역C_멤버A.getPoint()
					);
				}
			}

			@Nested
			class 여러달에걸친_적립내역들 {
				@Test
				public void 여러개의_적립내역이_있는경우_포인트조회_테스트() {
					//given
					var 멤버A_ID = 1L;
					var 멤버B_ID = 2L;

					var 회사A = Company.builder().name("A").build();
					var 회사B = Company.builder().name("B").build();

					여러회사_생성(회사A, 회사B);

					var 탄소중립포인트적립내역_두달전A_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(100)
						.activityType(ActivityType.ELECTRONIC_RECEIPT)
						.company(회사A)
						.build();

					var 탄소중립포인트적립내역_한달전A_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(100)
						.activityType(ActivityType.ELECTRONIC_RECEIPT)
						.company(회사A)
						.build();

					var 탄소중립포인트적립내역A_한달전B_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(300)
						.activityType(ActivityType.DISPOSABLE_CUP)
						.company(회사A)
						.build();

					var 탄소중립포인트적립내역_이번달A_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(1000)
						.activityType(ActivityType.REFILL_STATION)
						.company(회사B)
						.build();

					var 탄소중립포인트적립내역_이번달B_멤버A = Cpoint.builder()
						.memberId(멤버A_ID)
						.point(300)
						.activityType(ActivityType.DISPOSABLE_CUP)
						.company(회사A)
						.build();

					var 탄소중립포인트적립내역_이번달A_멤버B = Cpoint.builder()
						.memberId(멤버B_ID)
						.point(1000)
						.activityType(ActivityType.REFILL_STATION)
						.company(회사A)
						.build();

					여러탄소중립포인트적립내역_생성(
						탄소중립포인트적립내역_두달전A_멤버A,
						탄소중립포인트적립내역_한달전A_멤버A,
						탄소중립포인트적립내역A_한달전B_멤버A,
						탄소중립포인트적립내역_이번달A_멤버A,
						탄소중립포인트적립내역_이번달B_멤버A,
						탄소중립포인트적립내역_이번달A_멤버B
					);

					//JPA Auditing으로 인하여 이후 변경
					탄소중립포인트적립내역_두달전A_멤버A.minusMonthsCreatedAtForTest(2L);
					탄소중립포인트적립내역_한달전A_멤버A.minusMonthsCreatedAtForTest(1L);
					탄소중립포인트적립내역A_한달전B_멤버A.minusMonthsCreatedAtForTest(1L);

					//when
					Integer 결과_포인트 = cpointRepository.sumCpointByMemberIdWhereCreatedAtInThisMonth(멤버A_ID);

					//then
					assertThat(결과_포인트).isEqualTo(
						탄소중립포인트적립내역_이번달A_멤버A.getPoint()
							+ 탄소중립포인트적립내역_이번달B_멤버A.getPoint()
					);
				}
			}
		}
	}

	@Nested
	class findCpointSummaryByMemberId_성공_테스트 {

		@Nested
		class 한개이하의_활동요약 {
			@Test
			public void 적립내역이_없는경우_활동요약조회_테스트() {
				//given
				var 멤버A_ID = 1L;

				//when
				List<CpointSummaryDto> 활동요약 = cpointRepository.findCpointSummaryByMemberId(멤버A_ID);

				//then
				assertThat(활동요약).hasSize(0);
			}

			@Test
			public void 적립내역이_있는경우_활동요약조회_테스트() {
				//given
				var 멤버A_ID = 1L;
				var 회사A = Company.builder().name("A").build();

				단일회사_생성(회사A);

				var 탄소중립포인트적립내역A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(100)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역B = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(300)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역C = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(300)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.company(회사A)
					.build();

				여러탄소중립포인트적립내역_생성(
					탄소중립포인트적립내역A,
					탄소중립포인트적립내역B,
					탄소중립포인트적립내역C
				);

				//when
				List<CpointSummaryDto> 활동요약 = cpointRepository.findCpointSummaryByMemberId(멤버A_ID);

				//then
				assertThat(활동요약).hasSize(1);
				assertThat(활동요약)
					.usingRecursiveFieldByFieldElementComparator()
					.containsOnly(
						new CpointSummaryDto(
							ActivityType.ELECTRONIC_RECEIPT,
							탄소중립포인트적립내역A.getPoint().longValue()
								+ 탄소중립포인트적립내역B.getPoint().longValue()
								+ 탄소중립포인트적립내역C.getPoint().longValue()
						)
					);
			}
		}

		@Nested
		class 두개이상의_활동내역 {

			public void 여러개의_적립내역_활동요약조회_테스트() {
				//given
				var 멤버A_ID = 1L;
				var 멤버B_ID = 2L;

				var 회사A = Company.builder().name("A").build();
				var 회사B = Company.builder().name("B").build();

				여러회사_생성(회사A, 회사B);

				var 탄소중립포인트적립내역_전자영수증A_멤버A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(100)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역_전자영수증B_멤버A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(100)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.company(회사B)
					.build();

				var 탄소중립포인트적립내역_일회용컵반환A_멤버A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(300)
					.activityType(ActivityType.DISPOSABLE_CUP)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역_일회용컵반환B_멤버A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(300)
					.activityType(ActivityType.DISPOSABLE_CUP)
					.company(회사B)
					.build();

				var 탄소중립포인트적립내역_일회용컵반환C_멤버A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(300)
					.activityType(ActivityType.DISPOSABLE_CUP)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역_리필스테이션A_멤버A = Cpoint.builder()
					.memberId(멤버A_ID)
					.point(1000)
					.activityType(ActivityType.REFILL_STATION)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역_전자영수증A_멤버B = Cpoint.builder()
					.memberId(멤버B_ID)
					.point(100)
					.activityType(ActivityType.ELECTRONIC_RECEIPT)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역_일회용컵반환A_멤버B = Cpoint.builder()
					.memberId(멤버B_ID)
					.point(300)
					.activityType(ActivityType.DISPOSABLE_CUP)
					.company(회사A)
					.build();

				var 탄소중립포인트적립내역_리필스테이션A_멤버B = Cpoint.builder()
					.memberId(멤버B_ID)
					.point(1000)
					.activityType(ActivityType.REFILL_STATION)
					.company(회사A)
					.build();

				여러탄소중립포인트적립내역_생성(
					탄소중립포인트적립내역_전자영수증A_멤버A,
					탄소중립포인트적립내역_전자영수증B_멤버A,
					탄소중립포인트적립내역_일회용컵반환A_멤버A,
					탄소중립포인트적립내역_일회용컵반환B_멤버A,
					탄소중립포인트적립내역_일회용컵반환C_멤버A,
					탄소중립포인트적립내역_리필스테이션A_멤버A,
					탄소중립포인트적립내역_전자영수증A_멤버B,
					탄소중립포인트적립내역_일회용컵반환A_멤버B,
					탄소중립포인트적립내역_리필스테이션A_멤버B
				);

				//when
				List<CpointSummaryDto> 활동요약 = cpointRepository.findCpointSummaryByMemberId(멤버A_ID);

				//then
				assertThat(활동요약).hasSize(3);
				assertThat(활동요약)
					.usingRecursiveFieldByFieldElementComparator()
					.containsOnly(
						new CpointSummaryDto(
							ActivityType.ELECTRONIC_RECEIPT,
							탄소중립포인트적립내역_전자영수증A_멤버A.getPoint().longValue()
								+ 탄소중립포인트적립내역_전자영수증A_멤버B.getPoint().longValue()
						),
						new CpointSummaryDto(
							ActivityType.DISPOSABLE_CUP,
							탄소중립포인트적립내역_일회용컵반환A_멤버A.getPoint().longValue()
								+ 탄소중립포인트적립내역_일회용컵반환B_멤버A.getPoint().longValue()
						),
						new CpointSummaryDto(
							ActivityType.REFILL_STATION,
							탄소중립포인트적립내역_리필스테이션A_멤버A.getPoint().longValue()
						)
					);
			}
		}
	}
}
