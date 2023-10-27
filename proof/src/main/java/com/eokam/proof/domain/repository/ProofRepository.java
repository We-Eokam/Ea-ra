package com.eokam.proof.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.proof.domain.entity.Proof;

public interface ProofRepository extends JpaRepository<Proof, Long> {
}
