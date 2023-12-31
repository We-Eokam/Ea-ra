package com.eokam.proof.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.eokam.proof.application.dto.ProofCreateDto;
import com.eokam.proof.domain.constant.ActivityType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Proof {
	@Id
	@GeneratedValue
	private Long proofId;

	@Column(nullable = false)
	private Long memberId;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ActivityType activityType;

	@Column(name = "c_company_id")
	private Long cCompanyId;

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column(length = 20)
	private String contents;

	@Builder.Default
	@OneToMany(mappedBy = "proof", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProofImage> proofImages = new ArrayList<>();

	public static Proof from(ProofCreateDto proofCreateDto) {
		return Proof.builder()
			.memberId(proofCreateDto.memberId())
			.activityType(proofCreateDto.activityType())
			.cCompanyId(proofCreateDto.cCompanyId())
			.contents(proofCreateDto.content())
			.build();
	}
}
