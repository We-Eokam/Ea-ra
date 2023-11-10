package com.eokam.notification.application.notification.dto;

import java.time.LocalDateTime;

import com.eokam.notification.domain.notification.document.Notification;
import com.eokam.notification.infrastructure.accusation.constant.AccusationType;
import com.eokam.notification.infrastructure.accusation.dto.AccusationRequest;

import lombok.Builder;

@Builder
public record AccusationDto(Long sender, Long receiver, String content, AccusationType accusationType,
							LocalDateTime createdAt) {
	public static AccusationDto from(AccusationRequest accusationRequest) {
		return AccusationDto.builder()
			.sender(accusationRequest.sender())
			.receiver(accusationRequest.receiver())
			.accusationType(accusationRequest.accusationType())
			.build();
	}

	public static AccusationDto from(Notification notification) {
		return AccusationDto.builder()
			.sender(notification.getSender())
			.receiver(notification.getReceiver())
			.content(notification.getContent())
			.accusationType(notification.getAccusationType())
			.createdAt(notification.getCreatedAt().plusHours(9L))
			.build();
	}
}
