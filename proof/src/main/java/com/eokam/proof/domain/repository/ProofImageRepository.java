package com.eokam.proof.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.proof.domain.entity.ProofImage;

public interface ProofImageRepository extends JpaRepository<ProofImage, Long> {
}
