package com.eokam.groo.infrastructure.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeeklyProofCountDto {
	private Date date;
	private Long proofCount;
}
