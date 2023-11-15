package com.eokam.proof.infrastructure.external.cpoint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("cpoint-service")
public interface CPointServiceFeign {
	@PostMapping("/cpoint")
	SaveCpointResponse saveCpoint(@CookieValue(name = "access-token") String jwt, SaveCpointRequest saveCpointRequest);
}
