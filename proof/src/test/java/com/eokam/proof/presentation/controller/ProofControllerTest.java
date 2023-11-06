package com.eokam.proof.presentation.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.LongStream;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eokam.proof.application.dto.ProofCreateDto;
import com.eokam.proof.application.dto.ProofDto;
import com.eokam.proof.application.dto.ProofImageDto;
import com.eokam.proof.application.service.ProofService;
import com.eokam.proof.common.BaseControllerTest;
import com.eokam.proof.domain.constant.ActivityType;
import com.eokam.proof.infrastructure.util.error.ErrorCode;
import com.eokam.proof.infrastructure.util.error.GlobalExceptionHandler;
import com.eokam.proof.infrastructure.util.error.exception.ProofException;
import com.eokam.proof.presentation.dto.request.ProofCreateRequest;
import com.eokam.proof.presentation.dto.validator.ProofCreateRequestValidator;

import jakarta.servlet.http.Cookie;

class ProofControllerTest extends BaseControllerTest {

	@InjectMocks
	ProofController proofController;

	@Mock
	ProofService proofService;
	@Spy
	ProofCreateRequestValidator proofCreateRequestValidator;

	private static final List<ProofDto> EXPECTED_MY_PROOF_LIST = new ArrayList<>();
	private static final List<ProofDto> EXPECTED_FRIENDS_PROOF_LIST = new ArrayList<>();

	@BeforeEach
	public void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(proofController)
			.setControllerAdvice(GlobalExceptionHandler.class)
			.build();
	}

	@Test
	@DisplayName("내 인증 목록 조회 성공")
	@Transactional
	void getMyProofList_Success() throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		String testJwt = createJwt(1L);

		PageRequest pageRequest = PageRequest.of(0, 5);

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), EXPECTED_MY_PROOF_LIST.size());

		Page<ProofDto> proofPage = new PageImpl<>(EXPECTED_MY_PROOF_LIST.subList(start, end), pageRequest,
			EXPECTED_MY_PROOF_LIST.size());

		given(proofService.getMyProofList(anyString(), any(PageRequest.class)))
			.willReturn(proofPage);

		// when & then
		this.mockMvc.perform(get("/proof/me")
				.param("page", "0")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("proof")
				.isArray()
			)
			.andExpect(jsonPath("proof[0].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(0).proofId())
			)
			.andExpect(jsonPath("proof[0].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(0).activityType().name())
			)
			.andExpect(jsonPath("proof[0].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(0).cCompanyId())
			)
			.andExpect(jsonPath("proof[0].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(0).createdAt())
			)
			.andExpect(
				jsonPath("proof[0].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[0].picture[0].url")
					.value("http://test1.com")
			)
			.andExpect(
				jsonPath("proof[0].picture[0].name")
					.value("test1.jpg")
			)
			.andExpect(jsonPath("proof[0].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[1].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(1).proofId())
			)
			.andExpect(jsonPath("proof[1].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(1).activityType().name())
			)
			.andExpect(jsonPath("proof[1].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(1).cCompanyId())
			)
			.andExpect(jsonPath("proof[1].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(1).createdAt())
			)
			.andExpect(
				jsonPath("proof[1].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[1].picture[0].url")
					.value("http://test2.com")
			)
			.andExpect(
				jsonPath("proof[1].picture[0].name")
					.value("test2.jpg")
			)
			.andExpect(jsonPath("proof[1].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[2].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(2).proofId())
			)
			.andExpect(jsonPath("proof[2].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(2).activityType().name())
			)
			.andExpect(jsonPath("proof[2].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(2).cCompanyId())
			)
			.andExpect(jsonPath("proof[2].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(2).createdAt())
			)
			.andExpect(
				jsonPath("proof[2].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[2].picture[0].url")
					.value("http://test3.com")
			)
			.andExpect(
				jsonPath("proof[2].picture[0].name")
					.value("test3.jpg")
			)
			.andExpect(jsonPath("proof[2].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[3].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(3).proofId())
			)
			.andExpect(jsonPath("proof[3].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(3).activityType().name())
			)
			.andExpect(jsonPath("proof[3].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(3).cCompanyId())
			)
			.andExpect(jsonPath("proof[3].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(3).createdAt())
			)
			.andExpect(
				jsonPath("proof[3].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[3].picture[0].url")
					.value("http://test4.com")
			)
			.andExpect(
				jsonPath("proof[3].picture[0].name")
					.value("test4.jpg")
			)
			.andExpect(jsonPath("proof[3].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[4].proof_id")
				.value(EXPECTED_MY_PROOF_LIST.get(4).proofId())
			)
			.andExpect(jsonPath("proof[4].activity_type")
				.value(EXPECTED_MY_PROOF_LIST.get(4).activityType().name())
			)
			.andExpect(jsonPath("proof[4].c_company_id")
				.value(EXPECTED_MY_PROOF_LIST.get(4).cCompanyId())
			)
			.andExpect(jsonPath("proof[4].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(4).createdAt())
			)
			.andExpect(
				jsonPath("proof[4].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[4].picture[0].url")
					.value("http://test5.com")
			)
			.andExpect(
				jsonPath("proof[4].picture[0].name")
					.value("test5.jpg")
			)
			.andExpect(jsonPath("proof[4].content")
				.isEmpty()
			);

		verify(proofService).getMyProofList(testJwt, PageRequest.of(0, 5));
	}

	@Test
	@DisplayName("내 인증 목록 컨텐츠 없음")
	void getMyProofList_NO_CONTENT() throws Exception {
		String testJwt = createJwt(1L);

		PageRequest pageRequest = PageRequest.of(0, 5);

		List<ProofDto> proofDtoList = new ArrayList<>();

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), proofDtoList.size());

		Page<ProofDto> proofPage = new PageImpl<>(proofDtoList.subList(start, end), pageRequest,
			proofDtoList.size());

		given(proofService.getMyProofList(anyString(), any(PageRequest.class)))
			.willReturn(proofPage);

		// when & then
		this.mockMvc.perform(get("/proof/me")
				.param("page", "0")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		verify(proofService).getMyProofList(testJwt, PageRequest.of(0, 5));
	}

	@ParameterizedTest
	@CsvSource({"two, 1", "1, two"})
	@DisplayName("올바르지 않은 Query Param 을 입력 시 400 에러")
	void getMyProofList_400(String page, String size) throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		String testJwt = createJwt(1L);

		// when & then
		this.mockMvc.perform(get("/proof/me")
				.param("page", page)
				.param("size", size)
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("친구 인증 목록 조회 성공")
	@Transactional
	void getFriendsProofList_Success() throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		String testJwt = createJwt(1L);

		PageRequest pageRequest = PageRequest.of(0, 5);

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), EXPECTED_FRIENDS_PROOF_LIST.size());

		Page<ProofDto> proofPage = new PageImpl<>(EXPECTED_FRIENDS_PROOF_LIST.subList(start, end), pageRequest,
			EXPECTED_FRIENDS_PROOF_LIST.size());

		given(proofService.getProofList(anyString(), anyLong(), any(PageRequest.class)))
			.willReturn(proofPage);

		// when & then
		this.mockMvc.perform(get("/proof")
				.param("memberId", "2")
				.param("page", "0")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("proof")
				.isArray()
			)
			.andExpect(jsonPath("proof[0].proof_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(0).proofId())
			)
			.andExpect(jsonPath("proof[0].member_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(0).memberId())
			)
			.andExpect(jsonPath("proof[0].activity_type")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(0).activityType().name())
			)
			.andExpect(jsonPath("proof[0].c_company_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(0).cCompanyId())
			)
			.andExpect(jsonPath("proof[0].created_at")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(0).createdAt())
			)
			.andExpect(
				jsonPath("proof[0].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[0].picture[0].url")
					.value("http://test1.com")
			)
			.andExpect(
				jsonPath("proof[0].picture[0].name")
					.value("test1.jpg")
			)
			.andExpect(jsonPath("proof[0].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[1].proof_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(1).proofId())
			)
			.andExpect(jsonPath("proof[1].member_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(1).memberId())
			)
			.andExpect(jsonPath("proof[1].activity_type")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(1).activityType().name())
			)
			.andExpect(jsonPath("proof[1].c_company_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(1).cCompanyId())
			)
			.andExpect(jsonPath("proof[1].created_at")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(1).createdAt())
			)
			.andExpect(
				jsonPath("proof[1].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[1].picture[0].url")
					.value("http://test2.com")
			)
			.andExpect(
				jsonPath("proof[1].picture[0].name")
					.value("test2.jpg")
			)
			.andExpect(jsonPath("proof[1].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[2].proof_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(2).proofId())
			)
			.andExpect(jsonPath("proof[2].member_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(2).memberId())
			)
			.andExpect(jsonPath("proof[2].activity_type")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(2).activityType().name())
			)
			.andExpect(jsonPath("proof[2].c_company_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(2).cCompanyId())
			)
			.andExpect(jsonPath("proof[2].created_at")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(2).createdAt())
			)
			.andExpect(
				jsonPath("proof[2].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[2].picture[0].url")
					.value("http://test3.com")
			)
			.andExpect(
				jsonPath("proof[2].picture[0].name")
					.value("test3.jpg")
			)
			.andExpect(jsonPath("proof[2].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[3].proof_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(3).proofId())
			)
			.andExpect(jsonPath("proof[3].member_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(3).memberId())
			)
			.andExpect(jsonPath("proof[3].activity_type")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(3).activityType().name())
			)
			.andExpect(jsonPath("proof[3].c_company_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(3).cCompanyId())
			)
			.andExpect(jsonPath("proof[3].created_at")
				.value(EXPECTED_MY_PROOF_LIST.get(3).createdAt())
			)
			.andExpect(
				jsonPath("proof[3].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[3].picture[0].url")
					.value("http://test4.com")
			)
			.andExpect(
				jsonPath("proof[3].picture[0].name")
					.value("test4.jpg")
			)
			.andExpect(jsonPath("proof[3].content")
				.isEmpty()
			)
			.andExpect(jsonPath("proof[4].proof_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(4).proofId())
			)
			.andExpect(jsonPath("proof[4].member_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(4).memberId())
			)
			.andExpect(jsonPath("proof[4].activity_type")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(4).activityType().name())
			)
			.andExpect(jsonPath("proof[4].c_company_id")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(4).cCompanyId())
			)
			.andExpect(jsonPath("proof[4].created_at")
				.value(EXPECTED_FRIENDS_PROOF_LIST.get(4).createdAt())
			)
			.andExpect(
				jsonPath("proof[4].picture")
					.isArray()
			)
			.andExpect(
				jsonPath("proof[4].picture[0].url")
					.value("http://test5.com")
			)
			.andExpect(
				jsonPath("proof[4].picture[0].name")
					.value("test5.jpg")
			)
			.andExpect(jsonPath("proof[4].content")
				.isEmpty()
			);

		verify(proofService).getProofList(testJwt, 2L, PageRequest.of(0, 5));
	}

	@Test
	@DisplayName("친구 인증 목록 컨텐츠 없음")
	void getFriendsProofList_NO_CONTENT() throws Exception {
		String testJwt = createJwt(1L);

		PageRequest pageRequest = PageRequest.of(0, 5);

		List<ProofDto> proofDtoList = new ArrayList<>();

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), proofDtoList.size());

		Page<ProofDto> proofPage = new PageImpl<>(proofDtoList.subList(start, end), pageRequest,
			proofDtoList.size());

		given(proofService.getProofList(anyString(), anyLong(), any(PageRequest.class)))
			.willReturn(proofPage);

		// when & then
		this.mockMvc.perform(get("/proof")
				.param("memberId", "2")
				.param("page", "0")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		verify(proofService).getProofList(testJwt, 2L, PageRequest.of(0, 5));
	}

	@Test
	@DisplayName("친구 아닌 사용자 인증 목록 조회 실패")
	@Transactional
	void getProofList_Fail() throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		String testJwt = createJwt(1L);

		PageRequest pageRequest = PageRequest.of(0, 5);

		int start = (int)pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), EXPECTED_FRIENDS_PROOF_LIST.size());

		Page<ProofDto> proofPage = new PageImpl<>(EXPECTED_FRIENDS_PROOF_LIST.subList(start, end), pageRequest,
			EXPECTED_FRIENDS_PROOF_LIST.size());

		given(proofService.getProofList(anyString(), anyLong(), any(PageRequest.class)))
			.willThrow(new ProofException(ErrorCode.PROOF_NOT_AUTORIZED));

		// when & then
		this.mockMvc.perform(get("/proof")
				.param("memberId", "2")
				.param("page", "0")
				.param("size", "5")
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@ParameterizedTest
	@CsvSource({"two, two, 1", "two, 1, two", "2, two, 1", "2, 1, two"})
	@DisplayName("친구 인증 목록 조회에서 올바르지 않은 Query Param 을 입력 시 400 에러")
	void getFriendProofList_400(String memberId, String page, String size) throws Exception {
		LongStream.range(1, 6).forEach(this::generateProof);

		String testJwt = createJwt(1L);

		// when & then
		this.mockMvc.perform(get("/proof")
				.param("memberId", memberId)
				.param("page", page)
				.param("size", size)
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@ParameterizedTest
	@CsvSource({"ELECTRONIC_RECEIPT", "TUMBLER", "DISPOSABLE_CUP", "DISCARDED_PHONE", "ECO_FRIENDLY_PRODUCTS",
		"EMISSION_FREE_CAR", "HIGH_QUALITY_RECYCLED_PRODUCTS", "MULTI_USE_CONTAINER", "REFILL_STATION"})
	@DisplayName("인증 생성을 성공하는 테스트")
	void postCreateProof_Success(ActivityType activityType) throws Exception {
		final String testJwt = createJwt(1L);

		final Long EXPECTED_PROOF_ID = 1L;
		final Long EXPECTED_MEMBER_ID = 1L;
		final Long EXPECTED_CCOMPANY_ID = 1L;
		final ActivityType EXPECTED_ACTIVITY_TYPE = activityType;
		final String EXPECTED_CONTENT = null;
		final LocalDateTime EXPECTED_CREATED_AT = LocalDateTime.now();
		final String EXPECTED_FILE_URL = "http://test.com";
		final String EXPECTED_FILE_NAME = "test";
		final String EXPECTED_ORIGINAL_NAME = "test.jpg";
		final Long EXPECTED_IMAGE_ID = 1L;
		final List<ProofImageDto> EXPECTED_PROOF_IMAGES = new ArrayList<>() {{
			ProofImageDto.builder()
				.proofImageId(EXPECTED_IMAGE_ID)
				.fileUrl(EXPECTED_FILE_URL)
				.fileName(EXPECTED_FILE_NAME)
				.build();
		}};

		final ClassPathResource resource = new ClassPathResource("static/earth.jpg");

		final MockMultipartFile mockMultipartFile =
			new MockMultipartFile(
				"file",
				EXPECTED_ORIGINAL_NAME,
				MediaType.IMAGE_JPEG_VALUE,
				resource.getContentAsByteArray()
			);

		final ProofCreateRequest proofCreateRequest = ProofCreateRequest.builder()
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.cCompanyId(EXPECTED_CCOMPANY_ID)
			.content(EXPECTED_CONTENT)
			.build();

		final ProofDto expectedProofDto = ProofDto.builder()
			.proofId(EXPECTED_PROOF_ID)
			.memberId(EXPECTED_MEMBER_ID)
			.cCompanyId(EXPECTED_CCOMPANY_ID)
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.createdAt(EXPECTED_CREATED_AT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.proofImages(EXPECTED_PROOF_IMAGES)
			.content(EXPECTED_CONTENT)
			.build();

		given(proofService.createProof(argThat(proofDto -> proofDto.activityType().equals(activityType)), anyList()))
			.willReturn(expectedProofDto);

		JSONObject input = new JSONObject();
		input.put("activity_type", EXPECTED_ACTIVITY_TYPE.toString());
		input.put("c_company_id", EXPECTED_CCOMPANY_ID.toString());
		input.put("content", null);

		String requestDtoJson = input.toString();

		MockMultipartFile requestContent = new MockMultipartFile(
			"content",
			"",
			MediaType.APPLICATION_JSON_VALUE,
			requestDtoJson.getBytes(StandardCharsets.UTF_8)
		);

		doNothing().when(proofCreateRequestValidator).validate(any(ProofCreateRequest.class));

		this.mockMvc.perform(
				multipart("/proof")
					.file(mockMultipartFile)
					.file(requestContent)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.accept(MediaType.APPLICATION_JSON)
					.cookie(new Cookie("access-token", testJwt)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("proof_id")
				.value(EXPECTED_PROOF_ID)
			)
			.andExpect(jsonPath("activity_type")
				.value(EXPECTED_ACTIVITY_TYPE.name())
			)
			.andExpect(jsonPath("c_company_id")
				.value(EXPECTED_CCOMPANY_ID)
			)
			.andExpect(jsonPath("created_at")
				.value(EXPECTED_CREATED_AT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			)
			.andExpect(jsonPath("picture")
				.isArray()
			)
			.andExpect(jsonPath("content")
				.isEmpty()
			);

		List<MultipartFile> multipartFileList = new ArrayList<>();
		multipartFileList.add(mockMultipartFile);

		verify(proofService).createProof(ProofCreateDto.of(testJwt, proofCreateRequest), multipartFileList);
	}

	@Test
	@DisplayName("기타 인증 생성을 성공하는 테스트")
	void postCreateEtcProof_Success() throws Exception {
		final String testJwt = createJwt(1L);

		final Long EXPECTED_PROOF_ID = 1L;
		final Long EXPECTED_MEMBER_ID = 1L;
		final Long EXPECTED_COMPANY_ID = null;
		final ActivityType EXPECTED_ACTIVITY_TYPE = ActivityType.ETC;
		final String EXPECTED_CONTENT = "플로깅을 했어요!";
		final LocalDateTime EXPECTED_CREATED_AT = LocalDateTime.now();
		final String EXPECTED_FILE_URL = "http://test.com";
		final String EXPECTED_FILE_NAME = "test";
		final String EXPECTED_ORIGINAL_NAME = "test.jpg";
		final Long EXPECTED_IMAGE_ID = 1L;
		final List<ProofImageDto> EXPECTED_PROOF_IMAGES = new ArrayList<>() {{
			ProofImageDto.builder()
				.proofImageId(EXPECTED_IMAGE_ID)
				.fileUrl(EXPECTED_FILE_URL)
				.fileName(EXPECTED_FILE_NAME)
				.build();
		}};

		final ClassPathResource resource = new ClassPathResource("static/earth.jpg");

		final MockMultipartFile mockMultipartFile =
			new MockMultipartFile(
				"file",
				EXPECTED_ORIGINAL_NAME,
				MediaType.IMAGE_JPEG_VALUE,
				resource.getContentAsByteArray()
			);

		final ProofCreateRequest proofCreateRequest = ProofCreateRequest.builder()
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.cCompanyId(EXPECTED_COMPANY_ID)
			.content(EXPECTED_CONTENT)
			.build();

		final ProofDto expectedProofDto = ProofDto.builder()
			.proofId(EXPECTED_PROOF_ID)
			.memberId(EXPECTED_MEMBER_ID)
			.cCompanyId(EXPECTED_COMPANY_ID)
			.activityType(EXPECTED_ACTIVITY_TYPE)
			.createdAt(EXPECTED_CREATED_AT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.proofImages(EXPECTED_PROOF_IMAGES)
			.content(EXPECTED_CONTENT)
			.build();

		given(proofService.createProof(any(ProofCreateDto.class), anyList()))
			.willReturn(expectedProofDto);

		JSONObject input = new JSONObject();
		input.put("activity_type", EXPECTED_ACTIVITY_TYPE.toString());
		input.put("c_company_id", null);
		input.put("content", EXPECTED_CONTENT);

		String requestDtoJson = input.toString();

		MockMultipartFile requestContent = new MockMultipartFile(
			"content",
			"",
			MediaType.APPLICATION_JSON_VALUE,
			requestDtoJson.getBytes(StandardCharsets.UTF_8)
		);

		this.mockMvc.perform(multipart("/proof")
				.file(mockMultipartFile)
				.file(requestContent)
				.cookie(new Cookie("access-token", testJwt)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("proof_id")
				.value(EXPECTED_PROOF_ID)
			)
			.andExpect(jsonPath("activity_type")
				.value(EXPECTED_ACTIVITY_TYPE.name())
			)
			.andExpect(jsonPath("c_company_id")
				.isEmpty()
			)
			.andExpect(jsonPath("created_at")
				.value(EXPECTED_CREATED_AT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			)
			.andExpect(jsonPath("picture")
				.isArray()
			)
			.andExpect(jsonPath("content")
				.value(EXPECTED_CONTENT)
			);

		List<MultipartFile> multipartFileList = new ArrayList<>();
		multipartFileList.add(mockMultipartFile);

		verify(proofService).createProof(ProofCreateDto.of(testJwt, proofCreateRequest), multipartFileList);
	}

	@ParameterizedTest
	@CsvSource({"ELECTRONIC_RECEIPT, 1, 내용", "ETC, 1, 내용", "ELECTRONIC_RECEIPT, , ", "ETC, , ",
		"ELECTRONIC_RECEIPT, , 내용", "ETC, 1, "})
	@DisplayName("올바르지 않은 Request 요청 시 400 에러")
	void postCreateProof_400(ActivityType activityType, Long companyId, String content) throws Exception {
		final String testJwt = createJwt(1L);

		final Long EXPECTED_CCOMPANY_ID = companyId;
		final ActivityType EXPECTED_ACTIVITY_TYPE = activityType;
		final String EXPECTED_CONTENT = content;
		final String EXPECTED_FILE_NAME = "test";
		final String EXPECTED_ORIGINAL_NAME = "test.jpg";
		final ClassPathResource resource = new ClassPathResource("static/earth.jpg");

		final MockMultipartFile mockMultipartFile =
			new MockMultipartFile(
				EXPECTED_FILE_NAME,
				EXPECTED_ORIGINAL_NAME,
				MediaType.IMAGE_JPEG_VALUE,
				resource.getContentAsByteArray()
			);

		JSONObject input = new JSONObject();
		input.put("activity_type", EXPECTED_ACTIVITY_TYPE.toString());
		input.put("c_company_id", EXPECTED_CCOMPANY_ID);
		input.put("content", EXPECTED_CONTENT);

		String requestDtoJson = input.toString();

		MockMultipartFile requestContent = new MockMultipartFile(
			"content",
			"",
			MediaType.APPLICATION_JSON_VALUE,
			requestDtoJson.getBytes(StandardCharsets.UTF_8)
		);

		this.mockMvc.perform(multipart("/proof")
				.file(mockMultipartFile)
				.file(requestContent)
				.cookie(new Cookie("access-token", testJwt)))
			.andDo(print())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ProofException))
			.andReturn();
	}

	@Test
	@DisplayName("내 인증 상세 조회를 성공")
	void getMyProofDetail_Success() throws Exception {
		final String testJwt = createJwt(1L);

		List<ProofImageDto> EXPECTED_PROOF_IMAGES = new ArrayList<>();
		EXPECTED_PROOF_IMAGES.add(ProofImageDto.builder()
			.proofImageId(1L)
			.fileName("test.jpg")
			.fileUrl("http://test.com")
			.build());

		final ProofDto EXPECTED_PROOF = ProofDto.builder()
			.proofId(1L)
			.memberId(1L)
			.cCompanyId(1L)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.proofImages(EXPECTED_PROOF_IMAGES)
			.content(null)
			.build();

		given(proofService.getProofDetail(anyString(), anyLong())).willReturn(EXPECTED_PROOF);

		// when & then
		this.mockMvc.perform(get("/proof/" + 1)
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("proof_id")
				.value(EXPECTED_PROOF.proofId())
			)
			.andExpect(jsonPath("activity_type")
				.value(EXPECTED_PROOF.activityType().name())
			)
			.andExpect(jsonPath("c_company_id")
				.value(EXPECTED_PROOF.cCompanyId())
			)
			.andExpect(jsonPath("created_at")
				.value(EXPECTED_PROOF.createdAt())
			)
			.andExpect(jsonPath("picture")
				.isArray()
			)
			.andExpect(jsonPath("picture[0].url")
				.value("http://test.com")
			)
			.andExpect(jsonPath("picture[0].name")
				.value("test.jpg")
			)
			.andExpect(jsonPath("content")
				.isEmpty()
			);

		verify(proofService).getProofDetail(testJwt, 1L);
	}

	@Test
	@DisplayName("친구 인증 상세 조회를 성공")
	void getFriendProofDetail_Success() throws Exception {
		final String testJwt = createJwt(1L);

		List<ProofImageDto> EXPECTED_PROOF_IMAGES = new ArrayList<>();
		EXPECTED_PROOF_IMAGES.add(ProofImageDto.builder()
			.proofImageId(1L)
			.fileName("test.jpg")
			.fileUrl("http://test.com")
			.build());

		final ProofDto EXPECTED_PROOF = ProofDto.builder()
			.proofId(1L)
			.memberId(2L)
			.cCompanyId(1L)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.proofImages(EXPECTED_PROOF_IMAGES)
			.content(null)
			.build();

		given(proofService.getProofDetail(anyString(), anyLong())).willReturn(EXPECTED_PROOF);

		// when & then
		this.mockMvc.perform(get("/proof/" + 1)
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("proof_id")
				.value(EXPECTED_PROOF.proofId())
			)
			.andExpect(jsonPath("activity_type")
				.value(EXPECTED_PROOF.activityType().name())
			)
			.andExpect(jsonPath("c_company_id")
				.value(EXPECTED_PROOF.cCompanyId())
			)
			.andExpect(jsonPath("created_at")
				.value(EXPECTED_PROOF.createdAt())
			)
			.andExpect(jsonPath("picture")
				.isArray()
			)
			.andExpect(jsonPath("picture[0].url")
				.value("http://test.com")
			)
			.andExpect(jsonPath("picture[0].name")
				.value("test.jpg")
			)
			.andExpect(jsonPath("content")
				.isEmpty()
			);

		verify(proofService).getProofDetail(testJwt, 1L);
	}

	@Test
	@DisplayName("친구가 아닌 사용자의 인증 상세 조회를 실패")
	void getProofDetail_Fail() throws Exception {
		final String testJwt = createJwt(1L);

		List<ProofImageDto> EXPECTED_PROOF_IMAGES = new ArrayList<>();
		EXPECTED_PROOF_IMAGES.add(ProofImageDto.builder()
			.proofImageId(1L)
			.fileName("test.jpg")
			.fileUrl("http://test.com")
			.build());

		final ProofDto EXPECTED_PROOF = ProofDto.builder()
			.proofId(1L)
			.memberId(2L)
			.cCompanyId(1L)
			.activityType(ActivityType.ELECTRONIC_RECEIPT)
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.proofImages(EXPECTED_PROOF_IMAGES)
			.content(null)
			.build();

		given(proofService.getProofDetail(anyString(), anyLong())).willThrow(
			new ProofException(ErrorCode.PROOF_NOT_AUTORIZED));

		// when & then
		this.mockMvc.perform(get("/proof/" + 1)
				.cookie(new Cookie("access-token", testJwt))
			)
			.andDo(print())
			.andExpect(status().isUnauthorized())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof ProofException))
			.andReturn();

		verify(proofService).getProofDetail(testJwt, 1L);
	}

	private void generateProof(Long i) {
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
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.build();

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
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.build();

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
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.build();

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
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.build();

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
			.createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
			.build();

		if (i == 1) {
			EXPECTED_MY_PROOF_LIST.add(proof1);
			EXPECTED_MY_PROOF_LIST.add(proof2);
			EXPECTED_MY_PROOF_LIST.add(proof3);
			EXPECTED_MY_PROOF_LIST.add(proof4);
			EXPECTED_MY_PROOF_LIST.add(proof5);
		}

		if (i == 2) {
			EXPECTED_FRIENDS_PROOF_LIST.add(proof1);
			EXPECTED_FRIENDS_PROOF_LIST.add(proof2);
			EXPECTED_FRIENDS_PROOF_LIST.add(proof3);
			EXPECTED_FRIENDS_PROOF_LIST.add(proof4);
			EXPECTED_FRIENDS_PROOF_LIST.add(proof5);
		}
	}

	private static String createJwt(Long memberId) {
		byte[] payload = Base64.getEncoder().encode(Long.toString(memberId).getBytes());

		return "Header." + new String(payload, StandardCharsets.UTF_8) + ".Secret";
	}
}