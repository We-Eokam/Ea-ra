package com.eokam.accusation.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.eokam.accusation.infrastructure.client.dto.MemberClientRequest;

@FeignClient("member-service")
public interface MemberServiceClient {

	@PostMapping(value = "/member/accusation", consumes = "application/json")
	void isValidRequest(MemberClientRequest request);

}
