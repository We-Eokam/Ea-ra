package com.eokam.proof.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eokam.proof.domain.entity.Proof;

@Repository
public interface ProofRepository extends JpaRepository<Proof, Long> {
	Page<Proof> findAllByMemberId(Long memberId, Pageable pageable);

	Optional<Proof> findByProofId(Long proofId);

	@Query("SELECT p FROM Proof p WHERE p.memberId IN (:memberList)")
	Page<Proof> findAllByMemberList(List<Long> memberList, Pageable pageable);
}
