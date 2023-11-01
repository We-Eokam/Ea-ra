package com.eokam.cpoint.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.eokam.cpoint.infra.common.CommonRepositoryTest;

public class CompanyPoliciesRepositoryTest extends CommonRepositoryTest {

	@Nested
	class findCompanyPoliciesByActivityType_성공_테스트 {
		@Test
		public void 연계기업_인증종류로_조회테스트() {
			//given
			var 회사A = Company.builder().name("A").build();
			var 회사B = Company.builder().name("B").build();
			var 회사C = Company.builder().name("C").build();

			여러회사_생성(회사A, 회사B, 회사C);

			var 회사정책_회사A_전자영수증 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 점포")
				.build();

			var 회사정책_회사B_전자영수증 = CompanyPolicy
				.builder()
				.company(회사B)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 매장")
				.build();

			var 회사정책_회사C_전자영수증 = CompanyPolicy
				.builder()
				.company(회사C)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("서울시 전지역")
				.build();

			var 회사정책_회사A_텀블러 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.TUMBLER)
				.target("부산광역시 사하구")
				.build();

			var 회사정책_회사B_일회용컵반납 = CompanyPolicy
				.builder()
				.company(회사B)
				.activityType(ActivityType.DISPOSABLE_CUP)
				.target("경산시 남산면")
				.build();

			여러회사정책_생성(
				회사정책_회사A_전자영수증,
				회사정책_회사B_전자영수증,
				회사정책_회사C_전자영수증,
				회사정책_회사A_텀블러,
				회사정책_회사B_일회용컵반납
			);

			//when
			List<CompanyPolicy> 회사정책 = companyPolicyRepository.findCompanyPoliciesByActivityType(
				ActivityType.ELECTRONIC_RECEIPT);

			//then
			assertThat(회사정책)
				.usingRecursiveFieldByFieldElementComparator()
				.containsOnly(
					회사정책_회사A_전자영수증,
					회사정책_회사B_전자영수증,
					회사정책_회사C_전자영수증
				);

		}
	}

	@Nested
	class findCompanyPoliciesByCompanyId_성공_테스트 {
		@Test
		public void 연계기업으로_조회테스트() {
			//given
			var 회사A = Company.builder().name("A").build();
			var 회사B = Company.builder().name("B").build();

			여러회사_생성(회사A, 회사B);

			var 회사정책_회사A_전자영수증 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 점포")
				.build();

			var 회사정책_회사B_전자영수증 = CompanyPolicy
				.builder()
				.company(회사B)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 매장")
				.build();

			var 회사정책_회사A_텀블러 = CompanyPolicy
				.builder()
				.company(회사A)
				.activityType(ActivityType.TUMBLER)
				.target("부산광역시 사하구")
				.build();

			var 회사정책_회사B_일회용컵반납 = CompanyPolicy
				.builder()
				.company(회사B)
				.activityType(ActivityType.DISPOSABLE_CUP)
				.target("경산시 남산면")
				.build();

			여러회사정책_생성(
				회사정책_회사A_전자영수증,
				회사정책_회사B_전자영수증,
				회사정책_회사A_텀블러,
				회사정책_회사B_일회용컵반납
			);

			//when
			List<CompanyPolicy> 회사정책 = companyPolicyRepository.findCompanyPoliciesByCompanyId(
				회사A.getId()
			);

			//then
			assertThat(회사정책)
				.usingRecursiveFieldByFieldElementComparator()
				.containsOnly(
					회사정책_회사A_전자영수증,
					회사정책_회사A_텀블러
				);

		}
	}

}
