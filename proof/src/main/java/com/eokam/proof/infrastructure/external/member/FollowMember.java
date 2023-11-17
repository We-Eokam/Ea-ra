package com.eokam.proof.infrastructure.external.member;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FollowMember(Long memberId, Long groo, Long repayGroo, String profileImageUrl, String nickname) {
}
