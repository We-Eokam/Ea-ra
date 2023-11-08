package com.eokam.cpoint.presentation.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.eokam.cpoint.application.dto.CompanyDetailDto;
import com.eokam.cpoint.application.dto.CompanyDto;
import com.eokam.cpoint.application.service.CompanyService;
import com.eokam.cpoint.domain.ActivityType;
import com.eokam.cpoint.domain.Company;
import com.eokam.cpoint.domain.CompanyPolicy;
import com.eokam.cpoint.global.ErrorCode;
import com.eokam.cpoint.global.exception.BusinessException;
import com.eokam.cpoint.global.exception.JwtException;
import com.eokam.cpoint.presentation.common.BaseControllerTest;
import com.eokam.cpoint.presentation.dto.CompanyListResponse;
import com.eokam.cpoint.presentation.dto.MemberDto;

import jakarta.servlet.http.Cookie;

public class CompanyControllerTest extends BaseControllerTest {

	@MockBean
	private CompanyService companyService;

	@Test
	void 회사목록조회() throws Exception {
		//given
		List<CompanyDto> companyDtoList = new ArrayList<>();

		CompanyDto 스타벅스 = new CompanyDto(1L, "스타벅스", true);
		CompanyDto 메가커피 = new CompanyDto(2L, "메가커피", false);
		CompanyDto 더벤티 = new CompanyDto(3L, "더벤티", false);
		CompanyDto 이디야 = new CompanyDto(4L, "이디야", true);

		companyDtoList.add(스타벅스);
		companyDtoList.add(메가커피);
		companyDtoList.add(더벤티);
		companyDtoList.add(이디야);

		Long memberId = 10L;
		MemberDto expectedMemberDto = MemberDto.builder().memberId(10L).build();
		ActivityType expectedActivityType = ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS;
		CompanyListResponse expectedResponse = CompanyListResponse.from(companyDtoList);

		given(companyService.retrieveCompanyList
			(argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
				argThat(a -> a.equals(expectedActivityType))))
			.willReturn(companyDtoList);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/company")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.param("category", ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS.name()))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.company_list[*].id", hasItems(1, 2, 3, 4)))
				.andExpect(jsonPath("$.company_list[*].name", hasItems("스타벅스", "메가커피", "더벤티", "이디야")))
				.andExpect(jsonPath("$.company_list[*].is_connect", hasItems(true, false)))
				.andExpect(jsonPath("$.company_list[?(@.is_connect==false)].name", hasItems("메가커피", "더벤티")));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("company_list").type(JsonFieldType.ARRAY).description("회사 데이터 배열"),
					fieldWithPath("company_list.[].id").type(JsonFieldType.NUMBER).description("회사 PK"),
					fieldWithPath("company_list.[].name").type(JsonFieldType.STRING).description("회사 이름"),
					fieldWithPath("company_list.[].is_connect").type(JsonFieldType.BOOLEAN).description("연동 여부"),
					fieldWithPath("company_list.[].policies").ignored()
				)
			)
		);
	}

	@Test
	void 회사목록조회_잘못된_활동종류() throws Exception {
		//given
		List<CompanyDto> companyDtoList = new ArrayList<>();

		CompanyDto 스타벅스 = new CompanyDto(1L, "스타벅스", true);
		CompanyDto 메가커피 = new CompanyDto(2L, "메가커피", false);
		CompanyDto 더벤티 = new CompanyDto(3L, "더벤티", false);
		CompanyDto 이디야 = new CompanyDto(4L, "이디야", true);

		companyDtoList.add(스타벅스);
		companyDtoList.add(메가커피);
		companyDtoList.add(더벤티);
		companyDtoList.add(이디야);

		Long memberId = 10L;
		MemberDto expectedMemberDto = MemberDto.builder().memberId(10L).build();
		ActivityType expectedActivityType = ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS;
		CompanyListResponse expectedResponse = CompanyListResponse.from(companyDtoList);

		given(companyService.retrieveCompanyList
			(argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
				argThat(a -> a.equals(expectedActivityType))))
			.willReturn(companyDtoList);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/company")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키))
					.param("category", "HIGH_QUALITY_RECYCLED_PRODUCT"))
				.andExpect(status().isBadRequest())
				.andExpect(
					result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	void 회사목록조회_JWT없는_요청() throws Exception {
		//given
		List<CompanyDto> companyDtoList = new ArrayList<>();

		CompanyDto 스타벅스 = new CompanyDto(1L, "스타벅스", true);
		CompanyDto 메가커피 = new CompanyDto(2L, "메가커피", false);
		CompanyDto 더벤티 = new CompanyDto(3L, "더벤티", false);
		CompanyDto 이디야 = new CompanyDto(4L, "이디야", true);

		companyDtoList.add(스타벅스);
		companyDtoList.add(메가커피);
		companyDtoList.add(더벤티);
		companyDtoList.add(이디야);

		Long memberId = 10L;
		MemberDto expectedMemberDto = MemberDto.builder().memberId(10L).build();
		ActivityType expectedActivityType = ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS;
		CompanyListResponse expectedResponse = CompanyListResponse.from(companyDtoList);

		given(companyService.retrieveCompanyList
			(argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
				argThat(a -> a.equals(expectedActivityType))))
			.willReturn(companyDtoList);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/company")
					.param("category", "HIGH_QUALITY_RECYCLED_PRODUCT"))
				.andExpect(status().isUnauthorized())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof JwtException))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.body.detail", equalTo("JWT 쿠키를 찾을 수 없습니다.")));
	}

	@Test
	void 회사목록조회_JWT잘못된_요청_테스트() throws Exception {
		//given
		List<CompanyDto> companyDtoList = new ArrayList<>();

		CompanyDto 스타벅스 = new CompanyDto(1L, "스타벅스", true);
		CompanyDto 메가커피 = new CompanyDto(2L, "메가커피", false);
		CompanyDto 더벤티 = new CompanyDto(3L, "더벤티", false);
		CompanyDto 이디야 = new CompanyDto(4L, "이디야", true);

		companyDtoList.add(스타벅스);
		companyDtoList.add(메가커피);
		companyDtoList.add(더벤티);
		companyDtoList.add(이디야);

		Long memberId = 10L;
		MemberDto expectedMemberDto = MemberDto.builder().memberId(10L).build();
		ActivityType expectedActivityType = ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS;
		CompanyListResponse expectedResponse = CompanyListResponse.from(companyDtoList);

		given(companyService.retrieveCompanyList
			(argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
				argThat(a -> a.equals(expectedActivityType))))
			.willReturn(companyDtoList);

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
	void 회사상세조회() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
		CompanyDto 스타벅스dto = CompanyDto.of(스타벅스, false);

		List<CompanyPolicy> 스타벅스정책 = new ArrayList<>();

		CompanyPolicy 스타벅스_텀블러 = CompanyPolicy
			.builder()
			.company(스타벅스)
			.activityType(ActivityType.TUMBLER)
			.target("전국 점포")
			.build();
		CompanyPolicy 스타벅스_전자영수증 = CompanyPolicy
			.builder()
			.company(스타벅스)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.target("전국 점포")
			.build();

		스타벅스정책.add(스타벅스_텀블러);
		스타벅스정책.add(스타벅스_전자영수증);

		CompanyDetailDto 스타벅스_상세정보 = CompanyDetailDto.of(스타벅스dto, 스타벅스정책);
		Long memberId = 10L;
		MemberDto expectedMemberDto = MemberDto.builder().memberId(10L).build();
		ActivityType expectedActivityType = ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS;

		given(companyService.retrieveCompanyDetail(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId()))))
			.willReturn(스타벅스_상세정보);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/company/" + 스타벅스.getId())
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", equalTo(스타벅스.getId().intValue())))
				.andExpect(jsonPath("$.name", equalTo(스타벅스.getName())))
				.andExpect(jsonPath("$.is_connect", equalTo(false)))
				.andExpect(jsonPath("$.policies[*].activity_type",
					hasItems(스타벅스_전자영수증.getActivityType().name(), 스타벅스_텀블러.getActivityType().name())))
				.andExpect(jsonPath("$.policies[*].target",
					hasItems("전국 점포")));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("회사 PK"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("회사 이름"),
					fieldWithPath("is_connect").type(JsonFieldType.BOOLEAN).description("연동 여부"),
					fieldWithPath("policies.[].activity_type").type(JsonFieldType.STRING).description("활동 종류"),
					fieldWithPath("policies.[].target").type(JsonFieldType.STRING).description("대상 범위")
				)
			)
		);
	}

	@Test
	void 회사상세조회_없는_회사PK() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
		CompanyDto 스타벅스dto = CompanyDto.of(스타벅스, false);

		List<CompanyPolicy> 스타벅스정책 = new ArrayList<>();

		CompanyPolicy 스타벅스_텀블러 = CompanyPolicy
			.builder()
			.company(스타벅스)
			.activityType(ActivityType.TUMBLER)
			.target("전국 점포")
			.build();
		CompanyPolicy 스타벅스_전자영수증 = CompanyPolicy
			.builder()
			.company(스타벅스)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.target("전국 점포")
			.build();

		스타벅스정책.add(스타벅스_텀블러);
		스타벅스정책.add(스타벅스_전자영수증);

		CompanyDetailDto 스타벅스_상세정보 = CompanyDetailDto.of(스타벅스dto, 스타벅스정책);
		Long memberId = 10L;
		MemberDto expectedMemberDto = MemberDto.builder().memberId(10L).build();
		ActivityType expectedActivityType = ActivityType.HIGH_QUALITY_RECYCLED_PRODUCTS;

		given(companyService.retrieveCompanyDetail(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId() + 1))))
			.willThrow(new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/company/" + (스타벅스.getId() + 1))
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING).description("에러메시지 (존재하지 않는 회사입니다)"),
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
	void 회사상세조회_PK잘못됨() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
		CompanyDto 스타벅스dto = CompanyDto.of(스타벅스, false);

		List<CompanyPolicy> 스타벅스정책 = new ArrayList<>();

		CompanyPolicy 스타벅스_텀블러 = CompanyPolicy
			.builder()
			.company(스타벅스)
			.activityType(ActivityType.TUMBLER)
			.target("전국 점포")
			.build();
		CompanyPolicy 스타벅스_전자영수증 = CompanyPolicy
			.builder()
			.company(스타벅스)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.target("전국 점포")
			.build();

		스타벅스정책.add(스타벅스_텀블러);
		스타벅스정책.add(스타벅스_전자영수증);

		CompanyDetailDto 스타벅스_상세정보 = CompanyDetailDto.of(스타벅스dto, 스타벅스정책);
		Long memberId = 10L;

		given(companyService.retrieveCompanyDetail(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId()))))
			.willReturn(스타벅스_상세정보);

		//when & then
		ResultActions perform =
			mockMvc.perform(get("/cpoint/company/가나다")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING)
						.description("에러메시지 (companyId의 타입이 일치하지 않습니다.)"),
					subsectionWithPath("body.*").ignored(),
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
	void 회사연동() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
		CompanyDto 스타벅스dto = CompanyDto.of(스타벅스, true);

		Long memberId = 10L;

		given(companyService.connectCompany(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId()))))
			.willReturn(스타벅스dto);

		//when & then
		ResultActions perform =
			mockMvc.perform(post("/cpoint/company/" + 스타벅스.getId() + "/connect")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", equalTo(스타벅스.getId().intValue())))
				.andExpect(jsonPath("$.name", equalTo(스타벅스.getName())))
				.andExpect(jsonPath("$.is_connect", equalTo(true)));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("회사 PK"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("회사 이름"),
					fieldWithPath("is_connect").type(JsonFieldType.BOOLEAN).description("연동 여부"),
					fieldWithPath("policies").ignored()
				)
			)
		);
	}

	@Test
	void 회사연동_이미연동됨() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
		CompanyDto 스타벅스dto = CompanyDto.of(스타벅스, true);

		Long memberId = 10L;

		given(companyService.connectCompany(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId()))))
			.willThrow(new BusinessException(ErrorCode.COMPANY_ALREADY_CONNECTED));

		//when & then
		ResultActions perform =
			mockMvc.perform(post("/cpoint/company/" + 스타벅스.getId() + "/connect")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(
					result -> assertTrue(result.getResolvedException() instanceof BusinessException));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING).description("에러메시지 (이미 연동된 회사입니다)"),
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
	void 회사연동_없는회사() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();

		Long memberId = 10L;

		given(companyService.connectCompany(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId() + 1))))
			.willThrow(new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

		//when & then
		ResultActions perform =
			mockMvc.perform(post("/cpoint/company/" + (스타벅스.getId() + 1) + "/connect")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(
					result -> assertTrue(result.getResolvedException() instanceof BusinessException));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING).description("에러메시지 (존재하지 않는 회사입니다)"),
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
	void 회사연동_PK잘못됨() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
		CompanyDto 스타벅스dto = CompanyDto.of(스타벅스, true);

		Long memberId = 10L;

		given(companyService.connectCompany(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId()))))
			.willThrow(new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

		//when & then
		ResultActions perform =
			mockMvc.perform(post("/cpoint/company/가나다/connect")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(
					result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING)
						.description("에러메시지 (companyId의 타입이 일치하지 않습니다.)"),
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
	void 회사연동해제() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();
		CompanyDto 스타벅스dto = CompanyDto.of(스타벅스, false);

		Long memberId = 10L;

		given(companyService.disconnectCompany(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId()))))
			.willReturn(스타벅스dto);

		//when & then
		ResultActions perform =
			mockMvc.perform(delete("/cpoint/company/" + 스타벅스.getId() + "/connect")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", equalTo(스타벅스.getId().intValue())))
				.andExpect(jsonPath("$.name", equalTo(스타벅스.getName())))
				.andExpect(jsonPath("$.is_connect", equalTo(false)));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("회사 PK"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("회사 이름"),
					fieldWithPath("is_connect").type(JsonFieldType.BOOLEAN).description("연동 여부"),
					fieldWithPath("policies").ignored()
				)
			)
		);
	}

	@Test
	void 회사연동해제_연동되지않은회사() throws Exception {
		//given
		Company 스타벅스 = Company.builder().id(1L).name("스타벅스").build();

		Long memberId = 10L;

		given(companyService.disconnectCompany(
			argThat(memberDto -> memberDto.getMemberId().equals(memberId)),
			argThat(a -> a.equals(스타벅스.getId()))))
			.willThrow(new BusinessException(ErrorCode.COMPANY_NOT_CONNECTED));

		//when & then
		ResultActions perform =
			mockMvc.perform(delete("/cpoint/company/" + 스타벅스.getId() + "/connect")
					.cookie(new Cookie("access-token", 멤버Id가10인_JWT쿠키)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(
					result -> assertTrue(result.getResolvedException() instanceof BusinessException));

		perform.andDo(
			restDocs.document(
				responseFields(
					fieldWithPath("body").type(JsonFieldType.OBJECT).description("에러 정보"),
					fieldWithPath("body.title").type(JsonFieldType.STRING).description("Bad Request"),
					fieldWithPath("body.detail").type(JsonFieldType.STRING).description("에러메시지 (연동되지 않은 회사입니다)"),
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
