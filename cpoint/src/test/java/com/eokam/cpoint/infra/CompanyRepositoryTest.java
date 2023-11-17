package com.eokam.cpoint.infra;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.eokam.cpoint.application.dto.CompanyDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyConnect;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.eokam.cpoint.infra.common.CommonRepositoryTest;

public class CompanyRepositoryTest extends CommonRepositoryTest {

	@Nested
	class findCompaniesByActivityTypeAndMemberId_테스트 {

		@Test
		void 연동하지_않은_기업들만_조회_테스트() {
			//given
			Long 멤버A_ID = 1L;

			var 회사A = Company.builder().name("A").build();
			var 회사B = Company.builder().name("B").build();
			var 회사C = Company.builder().name("C").build();
			var 회사D = Company.builder().name("D").build();
			var 회사E = Company.builder().name("E").build();

			여러회사_생성(회사A, 회사B, 회사C, 회사D, 회사E);

			var 회사A_전자영수증 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 점포")
				.build();

			var 회사A_텀블러 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.TUMBLER)
				.target("전국 점포")
				.build();

			var 회사B_전자영수증 = CompanyPolicy
				.builder()
				.company(회사B)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("서울시 점포")
				.build();

			var 회사C_전자영수증 = CompanyPolicy
				.builder()
				.company(회사C)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("강원도 점포")
				.build();

			var 회사D_리필스테이션 = CompanyPolicy
				.builder()
				.company(회사D)
				.activityType(ActivityType.REFILL_STATION)
				.target("경기도 점포")
				.build();

			var 회사E_다회용기 = CompanyPolicy
				.builder()
				.company(회사E)
				.activityType(ActivityType.MULTI_USE_CONTAINER)
				.target("제주도 점포")
				.build();

			여러회사정책_생성(회사A_전자영수증, 회사A_텀블러, 회사B_전자영수증, 회사C_전자영수증, 회사D_리필스테이션, 회사E_다회용기);

			//when
			var 조회_결과 = companyRepository
				.findCompaniesByActivityTypeAndMemberId(ActivityType.ELECTRONIC_RECEIPT, 멤버A_ID);

			assertThat(조회_결과).hasSize(3);
			assertThat(조회_결과).extracting(CompanyDto::getIsConnect).containsOnly(false);
			assertThat(조회_결과).extracting(CompanyDto::getCompanyName).containsOnly("A", "B", "C");
		}

		@Test
		void 연동한_기업들만_조회_테스트() {
			//given
			var 멤버A_ID = 1L;
			var 멤버B_ID = 100L;

			var 회사A = Company.builder().name("A").build();
			var 회사B = Company.builder().name("B").build();
			var 회사C = Company.builder().name("C").build();
			var 회사D = Company.builder().name("D").build();
			var 회사E = Company.builder().name("E").build();

			여러회사_생성(회사A, 회사B, 회사C, 회사D, 회사E);

			var 회사A_전자영수증 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 점포")
				.build();

			var 회사A_텀블러 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.TUMBLER)
				.target("전국 점포")
				.build();

			var 회사B_전자영수증 = CompanyPolicy
				.builder()
				.company(회사B)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("서울시 점포")
				.build();

			var 회사C_전자영수증 = CompanyPolicy
				.builder()
				.company(회사C)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("강원도 점포")
				.build();

			var 회사D_리필스테이션 = CompanyPolicy
				.builder()
				.company(회사D)
				.activityType(ActivityType.REFILL_STATION)
				.target("경기도 점포")
				.build();

			var 회사E_다회용기 = CompanyPolicy
				.builder()
				.company(회사E)
				.activityType(ActivityType.MULTI_USE_CONTAINER)
				.target("제주도 점포")
				.build();

			여러회사정책_생성(회사A_전자영수증, 회사A_텀블러, 회사B_전자영수증, 회사C_전자영수증, 회사D_리필스테이션, 회사E_다회용기);

			var 멤버A_회사A연동 = CompanyConnect.builder().company(회사A).memberId(멤버A_ID).build();
			var 멤버A_회사B연동 = CompanyConnect.builder().company(회사B).memberId(멤버A_ID).build();
			var 멤버A_회사C연동 = CompanyConnect.builder().company(회사C).memberId(멤버A_ID).build();
			var 멤버A_회사D연동 = CompanyConnect.builder().company(회사D).memberId(멤버A_ID).build();

			여러연동_생성(멤버A_회사A연동, 멤버A_회사B연동, 멤버A_회사C연동, 멤버A_회사D연동);

			//when
			var 조회_결과 = companyRepository
				.findCompaniesByActivityTypeAndMemberId(ActivityType.ELECTRONIC_RECEIPT, 멤버A_ID);

			assertThat(조회_결과).hasSize(3);
			assertThat(조회_결과).extracting(CompanyDto::getIsConnect).containsOnly(true);
			assertThat(조회_결과).extracting(CompanyDto::getCompanyName).containsOnly("A", "B", "C");
		}

		@Test
		void 연동된_기업과_연동안된_기업_조회_테스트() {
			//given
			var 멤버A_ID = 1L;
			var 멤버B_ID = 100L;

			var 회사A = Company.builder().name("A").build();
			var 회사B = Company.builder().name("B").build();
			var 회사C = Company.builder().name("C").build();
			var 회사D = Company.builder().name("D").build();
			var 회사E = Company.builder().name("E").build();

			여러회사_생성(회사A, 회사B, 회사C, 회사D, 회사E);

			var 회사A_전자영수증 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 점포")
				.build();

			var 회사A_텀블러 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.TUMBLER)
				.target("전국 점포")
				.build();

			var 회사B_전자영수증 = CompanyPolicy
				.builder()
				.company(회사B)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("서울시 점포")
				.build();

			var 회사C_전자영수증 = CompanyPolicy
				.builder()
				.company(회사C)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("강원도 점포")
				.build();

			var 회사D_리필스테이션 = CompanyPolicy
				.builder()
				.company(회사D)
				.activityType(ActivityType.REFILL_STATION)
				.target("경기도 점포")
				.build();

			var 회사E_다회용기 = CompanyPolicy
				.builder()
				.company(회사E)
				.activityType(ActivityType.MULTI_USE_CONTAINER)
				.target("제주도 점포")
				.build();

			여러회사정책_생성(회사A_전자영수증, 회사A_텀블러, 회사B_전자영수증, 회사C_전자영수증, 회사D_리필스테이션, 회사E_다회용기);

			var 멤버B_회사A연동 = CompanyConnect.builder().company(회사B).memberId(멤버B_ID).build();
			var 멤버A_회사B연동 = CompanyConnect.builder().company(회사B).memberId(멤버A_ID).build();
			var 멤버A_회사C연동 = CompanyConnect.builder().company(회사C).memberId(멤버A_ID).build();
			var 멤버A_회사D연동 = CompanyConnect.builder().company(회사D).memberId(멤버A_ID).build();

			여러연동_생성(멤버B_회사A연동, 멤버A_회사B연동, 멤버A_회사C연동, 멤버A_회사D연동);

			//when
			var 조회_결과 = companyRepository
				.findCompaniesByActivityTypeAndMemberId(ActivityType.ELECTRONIC_RECEIPT, 멤버A_ID);

			assertThat(조회_결과).hasSize(3);
			assertThat(조회_결과)
				.filteredOn(companyDto -> companyDto.getIsConnect().equals(true))
				.hasSize(2);

			assertThat(조회_결과)
				.filteredOn(companyDto -> companyDto.getIsConnect().equals(false))
				.hasSize(1);

			assertThat(조회_결과)
				.filteredOn(companyDto -> companyDto.getIsConnect().equals(true))
				.extracting(CompanyDto::getCompanyName)
				.containsOnly("B", "C");
		}
	}

	@Nested
	class findCompanyByCompanyIdAndMemberId_테스트 {

		@Test
		void 연동하지_않은_기업_조회_테스트() {
			//given
			Long 멤버A_ID = 1L;

			var 회사A = Company.builder().name("A").build();
			var 회사B = Company.builder().name("B").build();

			여러회사_생성(회사A,회사B);

			var 멤버A_회사B연동 = CompanyConnect.builder().company(회사B).memberId(멤버A_ID).build();

			//when
			var 조회_결과 = companyRepository
				.findCompanyByCompanyIdAndMemberId(회사A.getId(),멤버A_ID);

			assertThat(조회_결과.get()).isNotNull();
			assertThat(조회_결과.get().getCompanyId()).isEqualTo(회사A.getId());
			assertThat(조회_결과.get().getCompanyName()).isEqualTo(회사A.getName());
			assertThat(조회_결과.get().getIsConnect()).isFalse();
		}

		@Test
		void 연동한_기업_조회_테스트() {
			//given
			var 멤버A_ID = 1L;

			var 회사A = Company.builder().id(1L).name("A").build();
			var 회사B = Company.builder().id(2L).name("B").build();

			여러회사_생성(회사A, 회사B);

			var 멤버A_회사A연동 = CompanyConnect.builder().company(회사A).memberId(멤버A_ID).build();
			var 멤버A_회사B연동 = CompanyConnect.builder().company(회사B).memberId(멤버A_ID).build();

			여러연동_생성(멤버A_회사A연동, 멤버A_회사B연동);

			//when
			var 조회_결과 = companyRepository
				.findCompanyByCompanyIdAndMemberId(회사A.getId(),멤버A_ID);

			assertThat(조회_결과.get()).isNotNull();
			assertThat(조회_결과.get().getCompanyId()).isEqualTo(회사A.getId());
			assertThat(조회_결과.get().getCompanyName()).isEqualTo(회사A.getName());
			assertThat(조회_결과.get().getIsConnect()).isTrue();
		}
		@Test
		void 기없없을때_조회_테스트() {
			//given
			var 멤버A_ID = 1L;

			//when
			var 조회_결과 = companyRepository
				.findCompanyByCompanyIdAndMemberId(100000000L,멤버A_ID);

			assertThat(조회_결과).isEmpty();
		}
	}
}
