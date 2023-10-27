package com.eokam.cpoint.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @NotNull(message="잘못된 JWT 쿠키")
    private Long memberId;

    @NotNull(message="잘못된 JWT 쿠키")
    private MemberRole memberRole;
}
