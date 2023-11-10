package com.eokam.notification.infrastructure.fcm.service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmMessageService {

	private final FirebaseMessaging firebaseMessaging;

	public void sendMessageTo(String targetToken, String title, String content) throws FirebaseMessagingException {
		firebaseMessaging.send(createMessage(targetToken, createNotification(title, content)));
	}

	private static Message createMessage(String targetToken, Notification notification) {
		return Message.builder()
			.setToken(targetToken)
			.setNotification(notification)
			.build();
	}

	private static Notification createNotification(String title, String content) {
		return Notification.builder()
			.setTitle(title)
			.setBody(content)
			.build();
	}
}
