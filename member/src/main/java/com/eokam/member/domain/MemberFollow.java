package com.eokam.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class MemberFollow extends BaseEntity{


	@Id
	@Column(name = "member_follow_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id")
	Member requestor;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	Member receiver;

	@Builder
	public MemberFollow(Long id, Member requestor, Member receiver) {
		this.id = id;
		this.requestor = requestor;
		this.receiver = receiver;
	}
}


