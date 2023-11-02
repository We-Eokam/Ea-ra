package com.eokam.accusation.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.accusation.domain.entity.Accusation;

public interface AccusationRepository extends JpaRepository<Accusation, Long> {
	List<Accusation> findByMemberId(Long memberId);
	
}