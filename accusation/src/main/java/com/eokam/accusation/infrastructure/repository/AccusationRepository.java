package com.eokam.accusation.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.accusation.domain.entity.Accusation;

public interface AccusationRepository extends JpaRepository<Accusation, Long> {
	Page<Accusation> findByMemberId(Long memberId, Pageable pageable);

	Optional<Accusation> findByAccusationId(Long accusationId);

}