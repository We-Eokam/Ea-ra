package com.eokam.proof.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import com.eokam.proof.util.DatabaseCleanUpExtension;

import io.restassured.RestAssured;

@ExtendWith(DatabaseCleanUpExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
@ActiveProfiles("test")
public class AcceptanceTest {

	@LocalServerPort
	int port;

	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	}

}
