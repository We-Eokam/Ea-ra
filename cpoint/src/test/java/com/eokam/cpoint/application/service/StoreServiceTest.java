package com.eokam.cpoint.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.eokam.cpoint.application.common.BaseServiceTest;
import com.eokam.cpoint.application.dto.ActivityStroeClassDto;
import com.eokam.cpoint.application.dto.StoreClassDto;
import com.eokam.cpoint.application.dto.StoreDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.infra.CompanyRepository;
import com.eokam.cpoint.infra.StoreRepository;

public class StoreServiceTest extends BaseServiceTest {

	@InjectMocks
	private StoreServiceImpl storeService;

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Nested
	class 주변매장_조회테스트 {

		@Test
		void 활동타입_없이_조회() {
			//given
			Integer radius = 100;
			Double longitude = 127.030454;
			Double latitude = 37.510223;

			Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
			Company 메가커피 = Company.builder().id(2L).name("메가커피").build();

			given(companyRepository.findById(1L)).willReturn(Optional.of(스타벅스));
			given(companyRepository.findById(2L)).willReturn(Optional.of(메가커피));

			List<StoreDto> storeDtoList = 회사PK가_1인스타벅스_2인메가커피_각매장2개생성();

			given(storeRepository.getStoresListByCurrentPositionAndRadius(latitude, longitude, radius)).willReturn(
				storeDtoList);

			//when
			List<StoreClassDto> storeClassDtoList = storeService.retrieveNearCpointStore(radius, latitude, longitude);

			//then
			verify(storeRepository).getStoresListByCurrentPositionAndRadius(latitude, longitude, radius);
			verify(companyRepository, atLeastOnce()).findById(1L);
			verify(companyRepository, atLeastOnce()).findById(2L);

			assertThat(storeClassDtoList).hasSize(4);
			assertThat(storeClassDtoList).extracting(StoreClassDto::getCompanyId).contains(1L, 2L);
			assertThat(storeClassDtoList).extracting(StoreClassDto::getCompanyName).contains("스타벅스", "메가커피");
			assertThat(storeClassDtoList).extracting(StoreClassDto::getLatitude)
				.allSatisfy(coordinateLat -> {
					assertThat(coordinateLat).isNotNull();
					assertThat(coordinateLat).isNotNull();
					assertThat(coordinateLat).isGreaterThanOrEqualTo(0);
				});
			assertThat(storeClassDtoList).extracting(StoreClassDto::getLongitude)
				.allSatisfy(coordinateLng -> {
					assertThat(coordinateLng).isNotNull();
					assertThat(coordinateLng).isNotNull();
					assertThat(coordinateLng).isGreaterThanOrEqualTo(0);
				});
		}

		@Test
		void 활동타입을_사용하여_조회() {
			//given
			Integer radius = 100;
			Double longitude = 127.030454;
			Double latitude = 37.510223;
			ActivityType activityType = ActivityType.TUMBLER;

			Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
			Company 메가커피 = Company.builder().id(2L).name("메가커피").build();

			given(companyRepository.findById(1L)).willReturn(Optional.of(스타벅스));
			given(companyRepository.findById(2L)).willReturn(Optional.of(메가커피));

			List<StoreDto> storeDtoList = 회사PK가_1인스타벅스_2인메가커피_각매장2개생성();

			given(storeRepository.getStoresListByCurrentPositionAndRadiusAndActivityType(latitude, longitude, radius,
				activityType)).willReturn(storeDtoList);

			//when
			List<StoreClassDto> storeClassDtoList = storeService.retrieveNearCpointStoreByActivityType(radius, latitude,
				longitude, activityType);

			//then
			verify(storeRepository).getStoresListByCurrentPositionAndRadiusAndActivityType(latitude, longitude, radius,
				activityType);
			verify(companyRepository, atLeastOnce()).findById(1L);
			verify(companyRepository, atLeastOnce()).findById(2L);

			assertThat(storeClassDtoList).hasSize(4);
			assertThat(storeClassDtoList).extracting(StoreClassDto::getCompanyId).contains(1L, 2L);
			assertThat(storeClassDtoList).extracting(StoreClassDto::getCompanyName).contains("스타벅스", "메가커피");
			assertThat(storeClassDtoList).extracting(StoreClassDto::getLatitude)
				.allSatisfy(coordinateLat -> {
					assertThat(coordinateLat).isNotNull();
					assertThat(coordinateLat).isNotNull();
					assertThat(coordinateLat).isGreaterThanOrEqualTo(0);
				});
			assertThat(storeClassDtoList).extracting(StoreClassDto::getLongitude)
				.allSatisfy(coordinateLng -> {
					assertThat(coordinateLng).isNotNull();
					assertThat(coordinateLng).isNotNull();
					assertThat(coordinateLng).isGreaterThanOrEqualTo(0);
				});
		}

		@Test
		void 모든활동타입을_사용하여_활동타입으로나눠_조회() {
			//given
			Integer radius = 100;
			Double longitude = 127.030454;
			Double latitude = 37.510223;

			Company 스타벅스_텀블러 = Company.builder().id(1L).name("스타벅스").build();
			Company 메가커피_텀블러 = Company.builder().id(2L).name("메가커피").build();

			Company CU_전자영수증 = Company.builder().id(3L).name("CU").build();
			Company 이마트_전자영수증 = Company.builder().id(4L).name("이마트").build();

			given(companyRepository.findById(1L)).willReturn(Optional.of(스타벅스_텀블러));
			given(companyRepository.findById(2L)).willReturn(Optional.of(메가커피_텀블러));
			given(companyRepository.findById(3L)).willReturn(Optional.of(CU_전자영수증));
			given(companyRepository.findById(4L)).willReturn(Optional.of(이마트_전자영수증));

			List<StoreDto> 텀블러_매장 = 회사PK가_1인스타벅스_2인메가커피_각매장2개생성();
			List<StoreDto> 전자영수증_매장 = 회사PK가_3인이마트_4인CU_각매장2개생성();

			given(storeRepository.getStoresListByCurrentPositionAndRadiusAndActivityType(latitude, longitude, radius,
				ActivityType.TUMBLER)).willReturn(텀블러_매장);

			given(storeRepository.getStoresListByCurrentPositionAndRadiusAndActivityType(latitude, longitude, radius,
				ActivityType.ELECTRONIC_RECEIPT)).willReturn(전자영수증_매장);

			//when
			List<ActivityStroeClassDto> 조회결과 =
				storeService.retrieveNearCpointStoreCategorizedByActivityType(radius, latitude, longitude);

			//then
			assertThat(조회결과).hasSize(2);
			assertThat(조회결과)
				.filteredOn(dto -> dto.getActivityType().equals(ActivityType.TUMBLER))
				.allSatisfy(dto -> assertThat(dto.getStores()).hasSize(4));

			assertThat(조회결과)
				.filteredOn(dto -> dto.getActivityType().equals(ActivityType.TUMBLER))
				.flatExtracting(ActivityStroeClassDto::getStores)
				.extracting(StoreClassDto::getCompanyId, StoreClassDto::getCompanyName)
				.containsOnly(tuple(1L, "스타벅스"), tuple(2L, "메가커피"));

			assertThat(조회결과)
				.filteredOn(dto -> dto.getActivityType().equals(ActivityType.ELECTRONIC_RECEIPT))
				.allSatisfy(dto -> assertThat(dto.getStores()).hasSize(4));

			assertThat(조회결과)
				.filteredOn(dto -> dto.getActivityType().equals(ActivityType.ELECTRONIC_RECEIPT))
				.flatExtracting(ActivityStroeClassDto::getStores)
				.extracting(StoreClassDto::getCompanyId, StoreClassDto::getCompanyName)
				.containsOnly(tuple(3L, "CU"), tuple(4L, "이마트"));
		}

		List<StoreDto> 회사PK가_1인스타벅스_2인메가커피_각매장2개생성() {
			List<StoreDto> storeDtoList = new ArrayList<>();

			StoreDto 스타벅스_강남점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 1L;
				}

				@Override
				public String getBranch() {
					return "강남타워GT점";
				}

				@Override
				public Double getDistance() {
					return 500.0;
				}

				@Override
				public Double getLatitude() {
					return 37.497957;
				}

				@Override
				public Double getLongitude() {
					return 127.026063;
				}
			};

			StoreDto 스타벅스_역삼대로점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 1L;
				}

				@Override
				public String getBranch() {
					return "역삼대로점";
				}

				@Override
				public Double getDistance() {
					return 150.0;
				}

				@Override
				public Double getLatitude() {
					return 37.501599;
				}

				@Override
				public Double getLongitude() {
					return 127.040338;
				}
			};

			StoreDto 메가커피_강남역점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 2L;
				}

				@Override
				public String getBranch() {
					return "강남역점";
				}

				@Override
				public Double getDistance() {
					return 500.0;
				}

				@Override
				public Double getLatitude() {
					return 37.497966;
				}

				@Override
				public Double getLongitude() {
					return 127.027369;
				}
			};

			StoreDto 메가커피_언주로점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 2L;
				}

				@Override
				public String getBranch() {
					return "언주로점";
				}

				@Override
				public Double getDistance() {
					return 150.0;
				}

				@Override
				public Double getLatitude() {
					return 37.497966;
				}

				@Override
				public Double getLongitude() {
					return 127.027369;
				}
			};

			storeDtoList.add(스타벅스_강남점);
			storeDtoList.add(스타벅스_역삼대로점);
			storeDtoList.add(메가커피_강남역점);
			storeDtoList.add(메가커피_언주로점);

			return storeDtoList;
		}

		List<StoreDto> 회사PK가_3인이마트_4인CU_각매장2개생성() {
			List<StoreDto> storeDtoList = new ArrayList<>();

			StoreDto 이마트_역삼점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 3L;
				}

				@Override
				public String getBranch() {
					return "역삼점";
				}

				@Override
				public Double getDistance() {
					return 500.0;
				}

				@Override
				public Double getLatitude() {
					return 37.497957;
				}

				@Override
				public Double getLongitude() {
					return 127.026063;
				}
			};

			StoreDto 이마트_가양점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 3L;
				}

				@Override
				public String getBranch() {
					return "가양점";
				}

				@Override
				public Double getDistance() {
					return 150.0;
				}

				@Override
				public Double getLatitude() {
					return 37.501599;
				}

				@Override
				public Double getLongitude() {
					return 127.040338;
				}
			};

			StoreDto CU_강남역점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 4L;
				}

				@Override
				public String getBranch() {
					return "강남역점";
				}

				@Override
				public Double getDistance() {
					return 500.0;
				}

				@Override
				public Double getLatitude() {
					return 37.497966;
				}

				@Override
				public Double getLongitude() {
					return 127.027369;
				}
			};

			StoreDto CU_신목동점 = new StoreDto() {
				@Override
				public Long getCompanyId() {
					return 4L;
				}

				@Override
				public String getBranch() {
					return "신목동점";
				}

				@Override
				public Double getDistance() {
					return 150.0;
				}

				@Override
				public Double getLatitude() {
					return 37.497966;
				}

				@Override
				public Double getLongitude() {
					return 127.027369;
				}
			};

			storeDtoList.add(이마트_가양점);
			storeDtoList.add(이마트_역삼점);
			storeDtoList.add(CU_강남역점);
			storeDtoList.add(CU_신목동점);

			return storeDtoList;
		}
	}
}