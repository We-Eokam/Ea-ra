package com.eokam.member.infra.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.eokam.member.application.service.MemberService;
import com.eokam.member.presentation.dto.RepayGrooRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MQService {

	private final MemberService memberService;

	@RabbitListener(queues = "repayGrooQueue")
	public void receiveMessage(RepayGrooRequest grooSavingRequest) {
		memberService.repayGroo(grooSavingRequest.getMemberId(),grooSavingRequest.getSavingType(),grooSavingRequest.getGroo());
		log.info("Data has been successfully saved to the GrooSaving. Saved Data: '{}'", grooSavingRequest);
	}
}

