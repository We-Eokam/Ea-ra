package com.eokam.accusation.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.accusation.domain.entity.AccusationImage;

public interface AccusationImageRepository extends JpaRepository<AccusationImage, Long> {
}