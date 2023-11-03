package com.eokam.groo.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.groo.domain.entity.GrooSaving;

public interface GrooSavingRepository extends JpaRepository<GrooSaving, Long> {
}
