package com.eokam.groo.infrastructure.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GrooDailySumAmountDto {
	private Date date;
	private Long proofSum;
	private Long proofCount;
	private Long accusationSum;
	private Long accusationCount;

}
