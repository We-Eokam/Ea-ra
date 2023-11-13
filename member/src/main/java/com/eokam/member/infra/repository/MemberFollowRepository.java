package com.eokam.member.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eokam.member.domain.MemberFollow;

public interface MemberFollowRepository extends JpaRepository<MemberFollow,Long> {

	Optional<MemberFollow> findMemberFollowByRequestorIdAndReceiverID(Long requestorId,Long receiverId);

}
