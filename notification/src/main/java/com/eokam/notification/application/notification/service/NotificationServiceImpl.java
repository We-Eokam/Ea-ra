package com.eokam.notification.application.notification.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eokam.notification.application.notification.dto.NotificationDto;
import com.eokam.notification.domain.notification.document.AccusationNotification;
import com.eokam.notification.domain.notification.document.FollowAcceptNotification;
import com.eokam.notification.domain.notification.document.FollowNotification;
import com.eokam.notification.domain.notification.document.Notification;
import com.eokam.notification.domain.notification.repository.NotificationRepository;
import com.eokam.notification.infrastructure.util.ParseJwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	@Override
	public NotificationDto saveAccusationNotification(NotificationDto notificationDto) {
		Notification notification = notificationRepository.save(
			AccusationNotification.from(notificationDto));
		return NotificationDto.from(notification);
	}

	@Override
	public NotificationDto saveFollowNotification(NotificationDto notificationDto) {
		Notification notification = notificationRepository.save(FollowNotification.from(notificationDto));
		return NotificationDto.from(notification);
	}

	@Override
	public NotificationDto saveFollowAcceptNotification(NotificationDto notificationDto) {
		Notification notification = notificationRepository.save(
			FollowAcceptNotification.from(notificationDto));
		return NotificationDto.from(notification);
	}

	@Override
	public List<NotificationDto> getNotification(String accessToken, LocalDate startDate, LocalDate endDate) {
		List<Notification> notifications = notificationRepository.findByReceiverAndCreatedAtBetweenOrderByCreatedAtDesc(
			ParseJwtUtil.parseMemberId(accessToken), startDate.atStartOfDay(), endDate.atStartOfDay());

		return notifications.stream()
			.map(NotificationDto::from)
			.toList();
	}

}
