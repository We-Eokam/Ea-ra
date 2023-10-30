package com.eokam.cpoint.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CCompanyListRetrieveRequest {

    @NotNull(message = "활동 타입이 필요합니다.")
    ActivityType activityType;

}
