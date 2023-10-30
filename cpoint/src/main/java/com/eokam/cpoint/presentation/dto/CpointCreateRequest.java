package com.eokam.cpoint.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpointCreateRequest {

    @NotNull(message = "memberId가 필요합니다.")
    private Long memberId;

    @NotNull(message="적립할 포인트가 필요합니다.")
    @Positive(message = "적립할 포인트는 양수여야합니다.")
    private Integer amount;

}
