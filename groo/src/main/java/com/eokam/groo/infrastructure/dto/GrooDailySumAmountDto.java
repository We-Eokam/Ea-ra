package com.eokam.groo.infrastructure.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GrooDailySumAmountDto {
	private Date date;
	private Long proofSum;
	private Long proofCount;
	private Long accusationSum;
	private Long accusationCount;

}
