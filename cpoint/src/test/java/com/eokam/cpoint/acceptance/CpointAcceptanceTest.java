package com.eokam.cpoint.acceptance;

import com.eokam.cpoint.presentation.dto.ActivityType;
import com.eokam.cpoint.presentation.dto.CCompanyDetailRequest;
import com.eokam.cpoint.presentation.dto.CCompanyListRetrieveRequest;
import com.eokam.cpoint.presentation.dto.CpointCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.List;

import static com.eokam.cpoint.acceptance.CpointSteps.탄소중립실천포인트를_조회하면;
import static com.eokam.cpoint.acceptance.CpointSteps.탄소중립실천포인트_연계기업_상세조회;
import static com.eokam.cpoint.common.CommonSteps.HTTP_상태코드를_검증한다;
import static com.eokam.cpoint.acceptance.CpointSteps.탄소중립실천포인트가_적립됨;
import static com.eokam.cpoint.acceptance.CpointSteps.탄소중립실천포인트_연계기업_목록조회;
import static com.eokam.cpoint.acceptance.CpointSteps.탄소중립실천포인트_활동요약_조회;
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

    private void 탄소중립실천포인트_연계기업_목록_조회_검증(final ExtractableResponse<Response> 응답){
        assertThat(응답.jsonPath().getList("companies")).hasSizeGreaterThan(0);
    }

    private void 탄소중립실천포인트_연계기업_상세_조회_검증(final ExtractableResponse<Response> 응답,final Long 기업PK){
        assertThat(응답.jsonPath().getInt("companyId")).isEqualTo(기업PK);
    }

    private void 탄소중립실천포인트_활동요약_조회_검증(final ExtractableResponse<Response> 응답,Integer 카테고리수){
        assertThat(응답.jsonPath().getList("categories")).hasSize(카테고리수);
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

    @Test
    void 예상탄소중립실천포인트를_적립할수있다(){
        //given
        var 생성_요청 = CpointCreateRequest
                .builder()
                .amount(100)
                .memberId(1L)
                .activityType(ActivityType.ELECTRONIC_RECEIPT)
                .build();

        //when
        var 생성_응답 = 탄소중립실천포인트가_적립됨(생성_요청);

        //then
        HTTP_상태코드를_검증한다(생성_응답,정상생성);

        var 요청_JWT_쿠키 = JWT_쿠키_생성(1L);
        var 조회_응답 = 탄소중립실천포인트를_조회하면(요청_JWT_쿠키);

        HTTP_상태코드를_검증한다(조회_응답, 정상조회);
        탄소중립실천포인트_조회_검증(조회_응답,1100);
    }

    @Test
    void 탄소중립실천포인트_연계기업_목록을_조회할수있다() {
        //given
        var 조회_요청 = CCompanyListRetrieveRequest
                .builder()
                .activityType(ActivityType.ELECTRONIC_RECEIPT)
                .build();

        //when
        var 조회_응답 = 탄소중립실천포인트_연계기업_목록조회(조회_요청);

        //then
        HTTP_상태코드를_검증한다(조회_응답, 정상조회);
        탄소중립실천포인트_연계기업_목록_조회_검증(조회_응답);
    }

    @Test
    void 탄소중립실천포인트_연계기업을_상세조회할수있다(){
        //given
        var 기업PK = 1L;

        var 조회_요청 = CCompanyDetailRequest
                .builder()
                .companyId(기업PK)
                .build();

        //then
        var 조회_응답 = 탄소중립실천포인트_연계기업_상세조회(조회_요청);

        //then
        HTTP_상태코드를_검증한다(조회_응답,정상조회);
        탄소중립실천포인트_연계기업_상세_조회_검증(조회_응답,기업PK);

    }

    @Test
    void 탄소중립실천포인트_활동요약을_얻을수있다(){
        //given
        List<CpointCreateRequest> 생성_요청_목록_리스트 = new ArrayList<>();

        var 생성_요청_전자영수증 = CpointCreateRequest
                .builder()
                .amount(100)
                .memberId(1L)
                .activityType(ActivityType.ELECTRONIC_RECEIPT)
                .build();

        var 생성_요청_텀블러 = CpointCreateRequest
                .builder()
                .amount(300)
                .memberId(1L)
                .activityType(ActivityType.TUMBLER)
                .build();

        var 생성_요청_다회용기 = CpointCreateRequest
                .builder()
                .amount(1000)
                .memberId(1L)
                .activityType(ActivityType.MULTI_USE_CONTAINER)
                .build();

        var 생성_요청_리필스테이션_이용 = CpointCreateRequest
                .builder()
                .amount(2000)
                .memberId(1L)
                .activityType(ActivityType.REFILL_STATION)
                .build();

        생성_요청_목록_리스트.add(생성_요청_전자영수증);
        생성_요청_목록_리스트.add(생성_요청_텀블러);
        생성_요청_목록_리스트.add(생성_요청_다회용기);
        생성_요청_목록_리스트.add(생성_요청_리필스테이션_이용);

        생성_요청_목록_리스트.forEach(
                (생성요청)-> HTTP_상태코드를_검증한다(탄소중립실천포인트가_적립됨(생성요청),정상생성)
        );

        //when
        var 조회_응답 = 탄소중립실천포인트_활동요약_조회(JWT_쿠키_생성(1L));

        //then
        HTTP_상태코드를_검증한다(조회_응답,정상조회);
        탄소중립실천포인트_활동요약_조회_검증(조회_응답,생성_요청_목록_리스트.size());
    }
}
