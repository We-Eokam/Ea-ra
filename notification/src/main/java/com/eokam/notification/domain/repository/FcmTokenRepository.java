package com.eokam.notification.domain.repository;

import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.eokam.notification.domain.entity.Token;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FcmTokenRepository {
	private final StringRedisTemplate tokenRedisTemplate;

	public void save(Token token) {
		tokenRedisTemplate.opsForValue()
			.set(token.memberId(), token.token());
	}

	public void delete(Long memberId) {
		tokenRedisTemplate.delete(String.valueOf(memberId));
	}

	public Optional<Token> findTokenByMemberId(Long memberId) {
		String token = tokenRedisTemplate.opsForValue().get(String.valueOf(memberId));

		if (token == null) {
			return Optional.empty();
		}
		return Optional.of(Token.of(memberId, token));
	}

}