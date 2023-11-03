package com.eokam.proof.infrastructure.external.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "follow")
public interface FollowServiceFeign {
	@GetMapping("/follow")
	FollowStatus isFollow(@CookieValue(name = "access-token") String jwt,
		@SpringQueryMap IsFollowRequest isFollowRequest);
}
