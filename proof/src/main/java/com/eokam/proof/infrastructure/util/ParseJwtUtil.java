package com.eokam.proof.infrastructure.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ParseJwtUtil {
	public static Long parseMemberId(String jwt) {
		String payload = jwt.split("\\.")[1];
		return Long.parseLong(new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8));
	}
}
