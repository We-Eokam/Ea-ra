package com.eokam.notification.application.notification.service;

import com.eokam.notification.application.notification.dto.AccusationDto;

public interface NotificationService {
	AccusationDto sendAccusation(AccusationDto accusationDto);
}
