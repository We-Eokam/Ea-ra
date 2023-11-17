package com.eokam.cpoint.presentation.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.eokam.cpoint.application.dto.ActivityStroeClassDto;
import com.eokam.cpoint.application.dto.StoreClassDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.presentation.common.BaseControllerTest;

import jakarta.servlet.http.Cookie;

@Disabled
public class StoreControllerTest extends BaseControllerTest {

	@Test
	void 매장조회() throws Exception {
		//given
		Integer radius = 100;
		Double latitude = 35.000000;
		Double longitude = 127.0000000;

		StoreClassDto 스타벅스_강남점 = StoreClassDto
			.builder()
			.longitude(35.000000)
			.latitude(127.000001)
			.companyId(1L)
			.companyName("스타벅스")
			.branchName("강남점")
			.distance(100.000)
			.build();

		StoreClassDto 메가커피_언주로점 = StoreClassDto
			.builder()
			.longitude(35.000000)
			.latitude(127.000001)
			.companyId(2L)
			.companyName("메가커피")
			.branchName("언주로점")
			.distance(110.000)
			.build();

		StoreClassDto 멀티캠퍼스_역삼 = StoreClassDto
			.builder()
			.longitude(35.000000)
			.latitude(127.000001)
			.companyId(3L)
			.companyName("멀티캠퍼스")
			.branchName("역삼캠퍼스")
			.distance(10.000)
			.build();

		StoreClassDto 멀티캠퍼스_선릉 = StoreClassDto
			.builder()
			.longitude(35.000000)
			.latitude(127.000001)
			.companyId(3L)
			.companyName("멀티캠퍼스")
			.branchName("선릉캠퍼스")
			.distance(20.000)
			.build();

		List<StoreClassDto> 텀블러_스타벅스_강남점 = new ArrayList<>();
		텀블러_스타벅스_강남점.add(스타벅스_강남점);
		ActivityStroeClassDto 텀블러 = ActivityStroeClassDto.builder()
			.stores(텀블러_스타벅스_강남점)
			.activityType(ActivityType.TUMBLER)
			.build();

		List<StoreClassDto> 전자영수증_메가커피_언주로점 = new ArrayList<>();
		전자영수증_메가커피_언주로점.add(메가커피_언주로점);
		ActivityStroeClassDto 전자영수증 = ActivityStroeClassDto.builder()
			.stores(전자영수증_메가커피_언주로점)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.build();

		List<StoreClassDto> 무공해차_멀티캠퍼스_역삼_선릉 = new ArrayList<>();
		무공해차_멀티캠퍼스_역삼_선릉.add(멀티캠퍼스_선릉);
		무공해차_멀티캠퍼스_역삼_선릉.add(멀티캠퍼스_역삼);
		ActivityStroeClassDto 무공해차 = ActivityStroeClassDto.builder()
			.stores(무공해차_멀티캠퍼스_역삼_선릉)
			.activityType(ActivityType.EMISSION_FREE_CAR)
			.build();

		List<ActivityStroeClassDto> activityStroeClassDtoList = new ArrayList<>();
		activityStroeClassDtoList.add(무공해차);
		activityStroeClassDtoList.add(텀블러);
		activityStroeClassDtoList.add(전자영수증);

		given(storeService
			.retrieveNearCpointStoreCategorizedByActivityType(radius, latitude, longitude))
			.willReturn(activityStroeClassDtoList);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/store")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.queryParam("radius", radius.toString())
					.queryParam("latitude", latitude.toString())
					.queryParam("longitude", longitude.toString()))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.store_list.length()", equalTo(3)))
				.andExpect(jsonPath("$.store_list[*].activity_type", hasItems(
					ActivityType.EMISSION_FREE_CAR.name(), ActivityType.TUMBLER.name()
					, ActivityType.ELECTRONIC_RECEIPT.name()
				)))
				.andExpect(jsonPath("$.store_list[*].stores[*].company_id", hasItems(1, 2, 3)))
				.andExpect(jsonPath("$.store_list[?(@.activity_type=='EMISSION_FREE_CAR')].stores[*].company_name",
					hasItem("멀티캠퍼스")))
				.andExpect(jsonPath("$.store_list[?(@.activity_type=='EMISSION_FREE_CAR' )].stores[*].branch_name",
					hasItems("역삼캠퍼스", "선릉캠퍼스")));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("store_list").type(JsonFieldType.ARRAY).description("주변 매장 리스트"),
					fieldWithPath("store_list.[].activity_type").type(JsonFieldType.STRING)
						.description("활동 종류(카테고리로 사용)"),
					fieldWithPath("store_list.[].stores").type(JsonFieldType.ARRAY).description("활동 종류에 해당하는 매장들"),
					fieldWithPath("store_list.[].stores.[].company_id").type(JsonFieldType.NUMBER).description("기업 PK"),
					fieldWithPath("store_list.[].stores.[].company_name").type(JsonFieldType.STRING).description("기업명"),
					fieldWithPath("store_list.[].stores.[].branch_name").type(JsonFieldType.STRING)
						.description("매장명(지점명)"),
					fieldWithPath("store_list.[].stores.[].latitude").type(JsonFieldType.NUMBER)
						.description("매장 위치 위도"),
					fieldWithPath("store_list.[].stores.[].longitude").type(JsonFieldType.NUMBER)
						.description("매장 위치 경도"),
					fieldWithPath("store_list.[].stores.[].distance").type(JsonFieldType.NUMBER).description("매장 거리")

				),
				queryParameters(
					parameterWithName("radius").description("반경(m단위) 0이상"),
					parameterWithName("latitude").description("현재 위치 위도"),
					parameterWithName("longitude").description("현재 위치 경도")
				)
			)
		);
	}

	@Test
	void 매장조회_쿼리스트링없음() throws Exception {
		//given
		Integer radius = 100;
		Double latitude = 35.000000;

		List<ActivityStroeClassDto> activityStroeClassDtoList = new ArrayList<>();

		given(storeService
			.retrieveNearCpointStoreCategorizedByActivityType(any(), any(), any()))
			.willReturn(activityStroeClassDtoList);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/store")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.queryParam("radius", radius.toString())
					.queryParam("latitude", latitude.toString()))
				.andExpect(status().isBadRequest())
				.andExpect(result ->
					assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING).description("에러메시지 (필드에 해당하는 메세지)"),
					subsectionWithPath("body.*").ignored(),
					subsectionWithPath("statusCode").ignored(),
					subsectionWithPath("headers").ignored(),
					subsectionWithPath("detailMessageCode").ignored(),
					subsectionWithPath("detailMessageArguments").ignored(),
					subsectionWithPath("titleMessageCode").ignored()

				)
			)
		);
	}

	@Test
	void 매장조회_잘못된쿼리스트링() throws Exception {
		//given
		Integer radius = 100;
		Double latitude = 35.000000;

		List<ActivityStroeClassDto> activityStroeClassDtoList = new ArrayList<>();

		given(storeService
			.retrieveNearCpointStoreCategorizedByActivityType(any(), any(), any()))
			.willReturn(activityStroeClassDtoList);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/store")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.queryParam("radius", radius.toString())
					.queryParam("latitude", latitude.toString())
					.queryParam("longitude", "나는문어꿈을꾸는문어"))
				.andExpect(status().isBadRequest())
				.andExpect(result ->
					assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING).description("에러메시지 (필드에 해당하는 메세지)"),
					subsectionWithPath("body.*").ignored(),
					subsectionWithPath("statusCode").ignored(),
					subsectionWithPath("headers").ignored(),
					subsectionWithPath("detailMessageCode").ignored(),
					subsectionWithPath("detailMessageArguments").ignored(),
					subsectionWithPath("titleMessageCode").ignored()

				)
			)
		);
	}
}
