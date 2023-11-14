package com.eokam.member.infra.dto;

public enum FollowStatus {

	FRIEND("FRIEND"),
	REQUEST("REQUEST"),
	ACCEPT("ACCEPT"),
	NOTHING("NOTHING");

	private final String name;

	FollowStatus(String name) {
		this.name = name;
	}

}
