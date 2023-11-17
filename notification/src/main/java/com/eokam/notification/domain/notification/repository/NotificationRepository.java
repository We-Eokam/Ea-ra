package com.eokam.notification.domain.notification.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eokam.notification.domain.notification.document.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {
	List<Notification> findByReceiverAndCreatedAtBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);
}
