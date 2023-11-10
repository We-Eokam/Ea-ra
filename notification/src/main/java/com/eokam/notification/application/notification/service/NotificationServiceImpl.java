package com.eokam.notification.application.notification.service;

import org.springframework.stereotype.Service;

import com.eokam.notification.application.notification.dto.AccusationDto;
import com.eokam.notification.domain.notification.document.Notification;
import com.eokam.notification.domain.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	@Override
	public AccusationDto sendAccusation(AccusationDto accusationDto) {
		Notification notification = notificationRepository.save(Notification.accusation(accusationDto));
		return AccusationDto.from(notification);
	}
}
