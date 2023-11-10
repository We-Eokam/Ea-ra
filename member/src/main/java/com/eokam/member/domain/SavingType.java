package com.eokam.member.domain;

public enum SavingType {

	ACCUSATION("ACCUSATION"),
	PROOF("PROOF");

	private final String name;

	SavingType(String name) {
		this.name = name;
	}
}
