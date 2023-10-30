package com.eokam.proof.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eokam.proof.domain.entity.Proof;

@Repository
public interface ProofRepository extends JpaRepository<Proof, Long> {
	List<Proof> findAllByMemberId(Long proofId);
}
