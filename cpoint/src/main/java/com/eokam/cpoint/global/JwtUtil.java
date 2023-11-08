package com.eokam.cpoint.global;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.eokam.cpoint.global.exception.JwtException;
import com.eokam.cpoint.presentation.dto.MemberDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtUtil {
	public static MemberDto parseMember(final String jwt) throws JsonProcessingException {
		String payload;
		try{
			payload = jwt.split("\\.")[1];
		}
		catch(ArrayIndexOutOfBoundsException exception){
			throw new JwtException(ErrorCode.JWT_WRONG_VALUE);
		}
		payload = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MemberDto memberDto;
		try{
			memberDto = objectMapper.readValue(payload, MemberDto.class);
		}
		catch(JsonProcessingException exception){
			throw new JwtException(ErrorCode.JWT_WRONG_VALUE);
		}
		return memberDto;
	}

}
