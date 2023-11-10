package com.eokam.notification.domain.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eokam.notification.domain.notification.document.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
