package com.eokam.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	private String profileImage;

	private Integer groo = 0;

	private Integer billCount = 0;

	private Integer bill = 0;

	@Builder
	public Member(Long id, String nickname, String profileImage, Integer groo, Integer billCount, Integer bill) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.groo = groo;
		this.billCount = billCount;
		this.bill = bill;
	}
}
