package com.eokam.notification.infrastructure.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("member-service")
public interface MemberServiceFeign {
	@GetMapping("/member")
	MemberDetailResponse getMemberDetail(@RequestParam Long memberId);
}
