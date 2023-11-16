package com.eokam.proof.infrastructure.external.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("member-service")
public interface FollowServiceFeign {
	@GetMapping("/member/follow")
	FollowStatus isFollow(@CookieValue(name = "access-token") String jwt, @RequestParam Long memberId);

	@GetMapping("/member/follow/list")
	FollowList getFriends(@CookieValue(name = "access-token") String jwt);

	@PostMapping(value = "/member/accusation/count", consumes = "application/json")
	void increaseAccusationCount(@CookieValue(name = "access-token") String jwt, IsFollowRequest isFollowRequest);
}
