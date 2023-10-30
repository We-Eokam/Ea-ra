package com.eokam.cpoint.acceptance;

import com.eokam.cpoint.presentation.dto.ActivityType;
import com.eokam.cpoint.presentation.dto.CpointCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.restassured.RestAssured;
import static com.eokam.cpoint.acceptance.CpointSteps.탄소중립실천포인트를_조회하면;
import static com.eokam.cpoint.common.CommonSteps.HTTP_상태코드를_검증한다;
import static com.eokam.cpoint.acceptance.CpointSteps.탄소중립실천포인트가_적립됨;
import static com.eokam.cpoint.common.CommonSteps.정상생성;
import static com.eokam.cpoint.common.CommonSteps.정상조회;
import static com.eokam.cpoint.common.CommonSteps.JWT_쿠키_생성;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CpointAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup(){
        RestAssured.port = port;
    }


    private void 탄소중립실천포인트_조회_검증(final ExtractableResponse<Response> 응답,final Integer 결과포인트){
        assertThat(응답.jsonPath().getInt("cpoint")).isEqualTo(결과포인트);
    }

    @Test
    void 예상탄소중립실천포인트를_볼수있다(){
        //given
        var 생성_요청 = CpointCreateRequest
                .builder()
                .amount(100)
                .memberId(1L)
                .activityType(ActivityType.ELECTRONIC_RECEIPT)
                .build();

        var 생성_응답 = 탄소중립실천포인트가_적립됨(생성_요청);
        HTTP_상태코드를_검증한다(생성_응답,정상생성);

        var 요청_JWT_쿠키 = JWT_쿠키_생성(1L);
        //when
        var 조회_응답 = 탄소중립실천포인트를_조회하면(요청_JWT_쿠키);

        //then
        HTTP_상태코드를_검증한다(조회_응답, 정상조회);
        탄소중립실천포인트_조회_검증(조회_응답,1100);
    }


}
