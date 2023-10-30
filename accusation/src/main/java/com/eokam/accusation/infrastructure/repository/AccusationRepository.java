package com.eokam.accusation.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.accusation.domain.entity.Accusation;

public interface AccusationRepository extends JpaRepository<Accusation, Long> {
}