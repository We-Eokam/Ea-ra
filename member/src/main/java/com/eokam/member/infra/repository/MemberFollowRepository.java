package com.eokam.member.infra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eokam.member.domain.MemberFollow;

@Repository
public interface MemberFollowRepository extends JpaRepository<MemberFollow,Long> {

	Optional<MemberFollow> findMemberFollowByRequestorIdAndReceiverId(Long requestorId,Long receiverId);

	@Query("select mf.receiver.id from MemberFollow mf where mf.requestor.id=:memberId and EXISTS"
		+ " (select mf2 from MemberFollow mf2 where mf2.receiver.id=:memberId and mf2.requestor.id=mf.receiver.id)")
	List<Long> retrieveFollowedMemberByMemberId(Long memberId);
}
