package com.eokam.cpoint.acceptance;

import com.eokam.cpoint.presentation.dto.CCompanyListRetrieveRequest;
import com.eokam.cpoint.presentation.dto.CpointCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class CpointSteps {
    public static ExtractableResponse<Response> 탄소중립실천포인트를_조회하면(String JWT_쿠키){
        return RestAssured.given()
                .cookie(JWT_쿠키)
                .when()
                .get("/cpoint")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 탄소중립실천포인트가_적립됨(final CpointCreateRequest 생성요청){
        return RestAssured.given()
                .body(생성요청)
                .when()
                .post("/cpoint")
                .then()
                .extract();
    }

    public static ExtractableResponse<Response> 탄소중립실천포인트_연계기업_목록조회(final CCompanyListRetrieveRequest 목록조회요청){
        return RestAssured.given()
                .queryParam("category",목록조회요청.getActivityType())
                .when()
                .get("/cpoint/company")
                .then()
                .extract();
    }
}
