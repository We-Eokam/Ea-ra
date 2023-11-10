package com.eokam.groo.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eokam.groo.application.dto.GrooSavingDto;
import com.eokam.groo.application.service.GrooSavingService;
import com.eokam.groo.presentation.dto.GrooSavingRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitGrooSavingListener {

	private final GrooSavingService grooSavingService;

	@RabbitListener(queues = "grooSavingQueue")
	public void receiveMessage(GrooSavingRequest grooSavingRequest) {
		GrooSavingDto grooSavingDto = grooSavingService.createGrooSaving(GrooSavingDto.from(grooSavingRequest));
		log.info("Data has been successfully saved to the GrooSaving. Saved Data: '{}'", grooSavingDto);
	}
}
