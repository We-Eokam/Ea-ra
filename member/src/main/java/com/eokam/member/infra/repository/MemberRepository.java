package com.eokam.member.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eokam.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

	public Optional<Member> findMemberByNickname(String nickname);

}
