package com.eokam.cpoint.presentation.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.eokam.cpoint.application.service.CompanyService;
import com.eokam.cpoint.application.service.CpointService;
import com.eokam.cpoint.application.service.StoreService;
import com.eokam.cpoint.config.RestDocsConfig;
import com.eokam.cpoint.global.WebConfig;
import com.eokam.cpoint.presentation.controller.CompanyController;
import com.eokam.cpoint.presentation.controller.CpointController;
import com.eokam.cpoint.presentation.controller.StoreController;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@AutoConfigureRestDocs
@Import({RestDocsConfig.class, WebConfig.class})
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest({CompanyController.class, CpointController.class, StoreController.class})
public class BaseControllerTest {

	protected static String 멤버Id가10인_JWT쿠키 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6MTB9.mmEC4b0afZan9cC90acvqCzaLNzQboEkJctrs44W6oI";
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	protected RestDocumentationResultHandler restDocs;

	@MockBean
	protected CompanyService companyService;

	@MockBean
	protected CpointService cpointService;

	@MockBean
	protected StoreService storeService;

	@BeforeEach
	void setUp(final WebApplicationContext context,
		final RestDocumentationContextProvider provider) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(MockMvcRestDocumentation.documentationConfiguration(provider))  // rest docs 설정 주입
			.alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함
			.alwaysDo(restDocs) // pretty 패턴과 문서 디렉토리 명 정해준것 적용
			.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
			.build();
	}
}