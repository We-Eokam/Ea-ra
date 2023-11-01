package com.eokam.proof.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eokam.proof.domain.entity.Proof;

@Repository
public interface ProofRepository extends JpaRepository<Proof, Long> {
	Page<Proof> findAllByMemberId(Long memberId, Pageable pageable);
}
