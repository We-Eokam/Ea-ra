package com.eokam.cpoint.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CCompanyDetailRequest {

    @NotNull(message = "회사 PK가 필요합니다.")
    Long companyId;

}
