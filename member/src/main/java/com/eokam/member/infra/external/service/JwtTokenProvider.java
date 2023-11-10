package com.eokam.member.infra.external.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eokam.member.global.ErrorCode;
import com.eokam.member.global.exception.JwtParseException;
import com.eokam.member.global.exception.LoginException;
import com.eokam.member.infra.dto.JwtMemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	@Value("${jwt.secret-key}")
	private String secretKeyString;

	private SecretKey secretKey;

	private final TokenBlackList tokenBlackList;
	private int tokenExpirationMsec = 1800000;  // 만료시간 30분

	@PostConstruct
	public void init(){
		byte[] keyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
		secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public String provideToken(JwtMemberDto jwtMemberDto){
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND, tokenExpirationMsec);
		Date expiryDate =  calendar.getTime();

		Claims claims = Jwts.claims();
		claims.put("memberId",jwtMemberDto.getMemberId());
		return Jwts
			.builder()
			.setSubject("eokam")
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.setClaims(claims)
			.signWith(secretKey,SignatureAlgorithm.HS256)
			.compact();
	}

	public JwtMemberDto parseJwtToken(String accessToken){
		try{
			var jws = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(accessToken);
			return new JwtMemberDto(jws.getBody().get("memberId",Long.class));
		}
		catch(JwtException ex){
			throw new JwtParseException(ErrorCode.JWT_PARSE_FAIL);
		}
	}

	public void validateToken(String accessToken){
		if(!tokenBlackList.validate(accessToken)){
			throw new LoginException(ErrorCode.TOKEN_ALREADY_LOGOUT);
		}

		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(accessToken);
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			throw new JwtParseException(ErrorCode.INVALID_JWT_TOKEN);
		}
	}

	public void invalidateToken(String accessToken){
		tokenBlackList.invalidate(accessToken);
	}
}
