package com.eokam.cpoint.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.eokam.cpoint.application.dto.StoreDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.eokam.cpoint.domain.Store;
import com.eokam.cpoint.infra.common.CommonRepositoryTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreRepositoryTest extends CommonRepositoryTest {

	@PersistenceContext
	EntityManager em;

	@BeforeEach
	void setup() {
		em.createNativeQuery(
			"CREATE ALIAS IF NOT EXISTS POINT"
				+ " FOR \"com.eokam.cpoint.infra.common.SpatialDistanceFunction.POINT\""
		).executeUpdate();
		em.createNativeQuery(
			"CREATE ALIAS IF NOT EXISTS ST_DISTANCE_SPHERE"
				+ " FOR \"com.eokam.cpoint.infra.common.SpatialDistanceFunction.ST_Distance_Sphere\""
		).executeUpdate();

		em.flush();
	}

	@Nested
	class getStoresListByCurrentPositionAndRadius_성공테스트 {
		@Test
		void 주변500m에_매장이없는경우_테스트() {
			//given
			var 메가커피 = Company.builder().name("메가커피").build();

			단일회사_생성(메가커피);

			var 현재_위도 = 37.501366;
			var 현재_경도 = 127.039558;

			var 메가커피_강남역점 = Store.builder()
				.branch("강남역점")
				.company(메가커피)
				.latitude(37.497966)
				.longitude(127.027369)
				.build();

			단일매장_생성(메가커피_강남역점);

			//when
			List<StoreDto> 조회_결과 = storeRepository.getStoresListByCurrentPositionAndRadius(현재_위도, 현재_경도,
				500);

			//then
			assertThat(조회_결과).isEmpty();

		}

		@Test
		void 주변500m에_매장이있는경우_테스트() {
			//given
			var 스타벅스 = Company.builder().name("스타벅스").build();
			var 메가커피 = Company.builder().name("메가커피").build();

			여러회사_생성(스타벅스, 메가커피);

			var 현재_위도 = 37.501366;
			var 현재_경도 = 127.039558;

			var 스타벅스_역삼대로점 = Store.builder()
				.branch("역삼대로점")
				.company(스타벅스)
				.latitude(37.501599)
				.longitude(127.040338)
				.build();

			var 메가커피_언주로점 = Store.builder()
				.branch("언주로점")
				.company(메가커피)
				.latitude(37.501982)
				.longitude(127.039783)
				.build();

			var 메가커피_강남역점 = Store.builder()
				.branch("강남역점")
				.company(메가커피)
				.latitude(37.497966)
				.longitude(127.027369)
				.build();

			여러매장_생성(스타벅스_역삼대로점, 메가커피_강남역점, 메가커피_언주로점);

			//when
			List<StoreDto> 조회_결과 = storeRepository.getStoresListByCurrentPositionAndRadius(현재_위도, 현재_경도,
				500);

			//then
			assertThat(조회_결과)
				.extracting(StoreDto::getBranch)
				.contains("언주로점", "역삼대로점");

		}
	}

	@Nested
	class getStoresListByCurrentPositionAndRadiusAndActivityType_성공테스트 {
		@Test
		void 주변500m에_활동종류에해당하는_매장이없는경우_테스트() {
			//given
			var 메가커피 = Company.builder().name("메가커피").build();

			단일회사_생성(메가커피);

			var 현재_위도 = 37.501366;
			var 현재_경도 = 127.039558;

			var 메가커피_강남역점 = Store.builder()
				.branch("강남역점")
				.company(메가커피)
				.latitude(37.497966)
				.longitude(127.027369)
				.build();
			단일매장_생성(메가커피_강남역점);

			//when
			List<StoreDto> 조회_결과 = storeRepository.getStoresListByCurrentPositionAndRadiusAndActivityType(현재_위도, 현재_경도,
				500, ActivityType.ELECTRONIC_RECEIPT);

			//then
			assertThat(조회_결과).isEmpty();

		}

		@Test
		void 주변500m에_활동종류에해당하는_매장이있는경우_테스트() {
			//given
			var 스타벅스 = Company.builder().name("스타벅스").build();
			var 메가커피 = Company.builder().name("메가커피").build();

			여러회사_생성(스타벅스, 메가커피);

			var 스타벅스_전자영수증 = CompanyPolicy.builder()
				.company(스타벅스)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("전국 지점")
				.build();

			var 스타벅스_텀블러 = CompanyPolicy.builder()
				.company(스타벅스)
				.activityType(ActivityType.TUMBLER)
				.target("전국 지점")
				.build();

			var 메가커피_전자영수증 = CompanyPolicy.builder()
				.company(메가커피)
				.activityType(ActivityType.ELECTRONIC_RECEIPT)
				.target("언주로점")
				.build();

			var 메가커피_일회용컵반환 = CompanyPolicy.builder()
				.company(메가커피)
				.activityType(ActivityType.DISPOSABLE_CUP)
				.target("염창역점")
				.build();

			여러회사정책_생성(스타벅스_전자영수증, 스타벅스_텀블러, 메가커피_일회용컵반환, 메가커피_전자영수증);

			var 현재_위도 = 37.501366;
			var 현재_경도 = 127.039558;

			var 스타벅스_역삼대로점 = Store.builder()
				.branch("역삼대로점")
				.company(스타벅스)
				.latitude(37.501599)
				.longitude(127.040338)
				.build();

			var 스타벅스_강남GT타워점 = Store.builder()
				.branch("강남GT타워점")
				.company(스타벅스)
				.latitude(37.497957)
				.longitude(127.026063)
				.build();

			var 메가커피_언주로점 = Store.builder()
				.branch("언주로점")
				.company(메가커피)
				.latitude(37.501982)
				.longitude(127.039783)
				.build();

			var 메가커피_강남역점 = Store.builder()
				.branch("강남역점")
				.company(메가커피)
				.latitude(37.497966)
				.longitude(127.027369)
				.build();

			여러매장_생성(스타벅스_역삼대로점, 메가커피_강남역점, 메가커피_언주로점, 스타벅스_강남GT타워점);

			//when
			List<StoreDto> 전자영수증_조회_결과 = storeRepository.getStoresListByCurrentPositionAndRadiusAndActivityType(현재_위도,
				현재_경도, 500, ActivityType.ELECTRONIC_RECEIPT);

			List<StoreDto> 일회용컵반환_조회_결과 = storeRepository.getStoresListByCurrentPositionAndRadiusAndActivityType(현재_위도,
				현재_경도, 500, ActivityType.DISPOSABLE_CUP);

			List<StoreDto> 텀블러_조회_결과 = storeRepository.getStoresListByCurrentPositionAndRadiusAndActivityType(현재_위도,
				현재_경도, 500, ActivityType.TUMBLER);
			//then
			assertThat(전자영수증_조회_결과)
				.extracting(StoreDto::getBranch, StoreDto::getCompanyId)
				.containsOnly(
					tuple(스타벅스_역삼대로점.getBranch(), 스타벅스.getId()),
					tuple(메가커피_언주로점.getBranch(), 메가커피.getId())
				);

			assertThat(일회용컵반환_조회_결과)
				.extracting(StoreDto::getBranch, StoreDto::getCompanyId)
				.containsOnly(
					tuple(메가커피_언주로점.getBranch(), 메가커피.getId())
				);

			assertThat(텀블러_조회_결과)
				.extracting(StoreDto::getBranch, StoreDto::getCompanyId)
				.containsOnly(
					tuple(스타벅스_역삼대로점.getBranch(), 스타벅스.getId())
				);

		}
	}
}
