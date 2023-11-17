package com.eokam.accusation.presentation.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eokam.accusation.application.dto.AccusationDto;
import com.eokam.accusation.global.constant.ActivityType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AccusationResponse {

	private Long accusationId;

	private Long witnessId;

	private Long memberId;

	private ActivityType activityType;

	private String activityDetail;

	private Map<String, String> imageList;

	private LocalDateTime createdAt;

	public static AccusationResponse from(AccusationDto accusationDto) {
		return AccusationResponse.builder()
			.accusationId(accusationDto.accusationId())
			.witnessId(accusationDto.witnessId())
			.memberId(accusationDto.memberId())
			.activityType(accusationDto.activityType())
			.activityDetail(accusationDto.activityDetail())
			.imageList(makeImageList(accusationDto.imageList()))
			.createdAt(accusationDto.createdAt())
			.build();
	}

	public static Map<String, String> makeImageList(List<String> fileUrls) {
		Map<String, String> map = new HashMap<>();
		int index = 1;
		for (String fileUrl : fileUrls) {
			StringBuilder sb = new StringBuilder();
			sb.append("imageURL_").append(index++);
			map.put(sb.toString(), fileUrl);
		}
		return map;
	}
}