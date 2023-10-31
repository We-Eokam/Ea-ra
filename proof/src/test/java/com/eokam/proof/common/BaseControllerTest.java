package com.eokam.proof.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.eokam.proof.util.DatabaseCleanUpExtension;

import io.restassured.RestAssured;

@ExtendWith({SpringExtension.class, DatabaseCleanUpExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsAutoConfiguration.class)
@ActiveProfiles("test")
@Disabled
public class BaseControllerTest {

	@LocalServerPort
	int port;

	@Autowired
	protected MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}
}
