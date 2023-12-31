package com.eokam.cpoint.acceptance;

import com.eokam.cpoint.presentation.dto.CCompanyDetailRequest;
import com.eokam.cpoint.presentation.dto.CCompanyListRetrieveRequest;
import com.eokam.cpoint.presentation.dto.CStoreNearbyListRetrieveRequest;
import com.eokam.cpoint.presentation.dto.CpointCreateRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class CpointSteps {
	public static ExtractableResponse<Response> 탄소중립실천포인트를_조회하면(String JWT_쿠키) {
		return RestAssured.given()
			.cookie(JWT_쿠키)
			.when()
			.get("/cpoint")
			.then()
			.extract();
	}

	public static ExtractableResponse<Response> 탄소중립실천포인트가_적립됨(final CpointCreateRequest 생성요청) {
		return RestAssured.given()
			.body(생성요청)
			.when()
			.post("/cpoint")
			.then()
			.extract();
	}

	public static ExtractableResponse<Response> 탄소중립실천포인트_연계기업_목록조회(final CCompanyListRetrieveRequest 목록조회요청) {
		return RestAssured.given()
			.queryParam("category", 목록조회요청.getActivityType())
			.when()
			.get("/cpoint/company")
			.then()
			.extract();
	}

	public static ExtractableResponse<Response> 탄소중립실천포인트_연계기업_상세조회(final CCompanyDetailRequest 상세조회요청) {
		return RestAssured.given()
			.queryParam("companyId", 상세조회요청.getCompanyId())
			.when()
			.get("/cpoint/company")
			.then()
			.extract();
	}

	public static ExtractableResponse<Response> 탄소중립실천포인트_활동요약_조회(String JWT_쿠키) {
		return RestAssured.given()
			.cookie(JWT_쿠키)
			.queryParam("type", "category")
			.when()
			.get("/cpoint")
			.then()
			.extract();
	}

	public static ExtractableResponse<Response> 주변_탄소중립실천포인트_연계매장_조회(final CStoreNearbyListRetrieveRequest 연계매장조회요청) {
		return RestAssured.given()
			.queryParam("radius", 연계매장조회요청.getRadius())
			.queryParam("latitude", 연계매장조회요청.getLatitude())
			.queryParam("longitude", 연계매장조회요청.getLongitude())
			.when()
			.get("/cpoint/store")
			.then()
			.extract();
	}
}
