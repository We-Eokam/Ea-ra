package com.eokam.cpoint.presentation.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.eokam.cpoint.application.dto.CpointDto;
import com.eokam.cpoint.application.dto.CpointSummaryDto;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.global.ErrorCode;
import com.eokam.cpoint.global.exception.BusinessException;
import com.eokam.cpoint.global.exception.JwtException;
import com.eokam.cpoint.presentation.common.BaseControllerTest;
import com.eokam.cpoint.presentation.dto.CpointCreateRequest;
import com.eokam.cpoint.presentation.dto.CpointCreateResponse;
import com.eokam.cpoint.presentation.dto.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import jakarta.servlet.http.Cookie;

public class CpointControllerTest extends BaseControllerTest {

	@Test
	void 탄소중립포인트조회() throws Exception {
		//given
		Long memberId = 10L;
		MemberDto memberDto = MemberDto.builder().memberId(10L).build();
		Integer cpoint = 1000;

		given(cpointService.retrieveCpoint(argThat(a -> a.getMemberId().equals(memberId))))
			.willReturn(cpoint);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.member_id", equalTo(memberId.intValue())))
				.andExpect(jsonPath("$.cpoint", equalTo(cpoint)));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 ID"),
					fieldWithPath("cpoint").type(JsonFieldType.NUMBER).description("탄소 중립 포인트")
				)
			)
		);
	}

	@Test
	void 탄소중립포인트조회_JWT없음() throws Exception {
		//given
		Long memberId = 10L;
		MemberDto memberDto = MemberDto.builder().memberId(10L).build();
		Integer cpoint = 1000;

		given(cpointService.retrieveCpoint(memberDto))
			.willReturn(cpoint);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint"))
				.andExpect(status().isUnauthorized())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof JwtException))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.body.detail", equalTo("JWT 쿠키를 찾을 수 없습니다.")));

	}

	@Test
	void 탄소중립포인트조회_잘못된JWT() throws Exception {
		//given
		Long memberId = 10L;
		MemberDto memberDto = MemberDto.builder().memberId(10L).build();
		Integer cpoint = 1000;

		given(cpointService.retrieveCpoint(memberDto))
			.willReturn(cpoint);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/company")
					.cookie(new Cookie("access-token", "12345123"))
					.param("category", "HIGH_QUALITY_RECYCLED_PRODUCT"))
				.andExpect(status().isUnauthorized())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof JwtException))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

	}

	@Test
	void 탄소중립포인트적립() throws Exception {
		//given
		Long memberId = 10L;
		MemberDto memberDto = MemberDto.builder().memberId(10L).build();
		Integer cpoint = 1000;

		CpointCreateRequest cpointCreateRequest = CpointCreateRequest
			.builder()
			.memberId(memberId)
			.activityType(ActivityType.TUMBLER)
			.companyId(10L)
			.amount(cpoint)
			.build();

		CpointDto cpointDto = CpointDto.of(cpointCreateRequest, memberDto);

		CpointCreateResponse cpointCreateResponse = CpointCreateResponse.from(cpointDto);

		given(cpointService.saveCpoint(any()))
			.willReturn(cpointDto);
		ObjectMapper tempMapper = new ObjectMapper();
		tempMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		String body = tempMapper.writeValueAsString(cpointCreateRequest);

		//when & then
		ResultActions perform =
			mockMvc.perform(post("/cpoint")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.member_id", equalTo(memberId.intValue())))
				.andExpect(jsonPath("$.point", equalTo(cpoint)))
				.andExpect(jsonPath("$.company_id", equalTo(cpointDto.getCompanyId().intValue())))
				.andExpect(jsonPath("$.activity_type", equalTo(cpointDto.getActivityType().name())));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("point").type(JsonFieldType.NUMBER).description("적립한 탄소 중립 포인트"),
					fieldWithPath("company_id").type(JsonFieldType.NUMBER).description("회사 PK"),
					fieldWithPath("activity_type").type(JsonFieldType.STRING).description("활동 종류")

				),
				requestFields(
					fieldWithPath("member_id").type(JsonFieldType.NUMBER).description("멤버 PK"),
					fieldWithPath("amount").type(JsonFieldType.NUMBER).description("적립할 포인트"),
					fieldWithPath("company_id").type(JsonFieldType.NUMBER).description("회사 PK"),
					fieldWithPath("activity_type").type(JsonFieldType.STRING).description("활동 종류")
				)
			)
		);
	}

	@Test
	void 탄소중립포인트적립_잘못된Payload() throws Exception {
		//given
		Long memberId = 10L;
		MemberDto memberDto = MemberDto.builder().memberId(10L).build();
		Integer cpoint = 1000;

		CpointCreateRequest cpointCreateRequest = CpointCreateRequest
			.builder()
			.memberId(memberId)
			.activityType(ActivityType.TUMBLER)
			.companyId(10L)
			.build();

		CpointDto cpointDto = CpointDto.of(cpointCreateRequest, memberDto);

		CpointCreateResponse cpointCreateResponse = CpointCreateResponse.from(cpointDto);

		given(cpointService.saveCpoint(cpointDto))
			.willReturn(cpointDto);

		ObjectMapper tempMapper = new ObjectMapper();
		tempMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		String body = tempMapper.writeValueAsString(cpointCreateRequest);
		//when & then
		ResultActions perform =
			mockMvc.perform(post("/cpoint")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isBadRequest())
				.andExpect(
					result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

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
	void 탄소중립포인트적립_잘못된회사PK() throws Exception {
		//given
		Long memberId = 10L;
		MemberDto memberDto = MemberDto.builder().memberId(10L).build();
		Integer cpoint = 1000;

		CpointCreateRequest cpointCreateRequest = CpointCreateRequest
			.builder()
			.memberId(memberId)
			.activityType(ActivityType.TUMBLER)
			.companyId(9L)
			.amount(cpoint)
			.build();

		CpointDto cpointDto = CpointDto.of(cpointCreateRequest, memberDto);

		CpointCreateResponse cpointCreateResponse = CpointCreateResponse.from(cpointDto);

		given(cpointService.saveCpoint(any()))
			.willThrow(new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		String body = objectMapper.writeValueAsString(cpointCreateRequest);

		//when & then
		ResultActions perform =
			mockMvc.perform(post("/cpoint")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isBadRequest())
				.andExpect(
					result -> assertTrue(result.getResolvedException() instanceof BusinessException))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING).description("존재하지 않는 회사입니다."),
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
	void 탄소중립활동요약조회() throws Exception {
		//given
		Long memberId = 10L;
		MemberDto memberDto = MemberDto.builder().memberId(10L).build();

		List<CpointSummaryDto> cpointSummaryDtoList = new ArrayList<>();

		CpointSummaryDto 텀블러 = new CpointSummaryDto(ActivityType.TUMBLER, 1200L);
		CpointSummaryDto 전자영수증 = new CpointSummaryDto(ActivityType.ELECTRONIC_RECEIPT, 2000L);
		CpointSummaryDto 폐휴대폰 = new CpointSummaryDto(ActivityType.DISCARDED_PHONE, 1000L);
		CpointSummaryDto 다회용기 = new CpointSummaryDto(ActivityType.MULTI_USE_CONTAINER, 2100L);

		cpointSummaryDtoList.add(텀블러);
		cpointSummaryDtoList.add(전자영수증);
		cpointSummaryDtoList.add(폐휴대폰);
		cpointSummaryDtoList.add(다회용기);

		given(cpointService.retrieveCpointSummary(argThat(a -> a.getMemberId().equals(memberDto.getMemberId()))))
			.willReturn(cpointSummaryDtoList);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/summary")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(jsonPath("summary_list[*].activity_type", hasItems(
					ActivityType.TUMBLER.name(), ActivityType.ELECTRONIC_RECEIPT.name(),
					ActivityType.DISCARDED_PHONE.name(), ActivityType.MULTI_USE_CONTAINER.name())
				))
				.andExpect(jsonPath("summary_list[*].point", hasItems(
					1200, 2000, 2100, 1000
				)));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("summary_list").type(JsonFieldType.ARRAY).description("요약 정보 리스트"),
					fieldWithPath("summary_list.[].activity_type").type(JsonFieldType.STRING)
						.description("활동 타입 (activity_type)"),
					fieldWithPath("summary_list.[].point").type(JsonFieldType.NUMBER).description("활동타입에 해당하는 총 합계 포인트")

				)
			)
		);
	}

}
