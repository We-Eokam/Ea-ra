package com.eokam.proof.infrastructure.external.groo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("groo-service")
public interface GrooServiceFeign {
	@PostMapping("/groo")
	GrooSaveResponse saveGroo(@CookieValue(name = "access-token") String jwt, GrooSaveRequest grooSaveRequest);
}
