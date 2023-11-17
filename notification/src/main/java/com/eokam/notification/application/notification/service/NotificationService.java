package com.eokam.notification.application.notification.service;

import java.time.LocalDate;
import java.util.List;

import com.eokam.notification.application.notification.dto.NotificationDto;

public interface NotificationService {
	NotificationDto saveAccusationNotification(NotificationDto notificationDto);

	NotificationDto saveFollowNotification(NotificationDto notificationDto);

	NotificationDto saveFollowAcceptNotification(NotificationDto notificationDto);

	List<NotificationDto> getNotification(String accessToken, LocalDate startDate, LocalDate endDate);
}
