package com.eokam.member.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.eokam.member.domain.Member;

@EnableJpaAuditing
@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void 닉네임으로_멤버가져오기(){
		//given
		String 닉네임 = "나는문어";
		Member member = Member.builder().nickname("나는문어").build();
		memberRepository.save(member);

		//when
		Optional<Member> 결과 = memberRepository
			.findMemberByNickname(닉네임);

		//then
		assertThat(결과).isNotEmpty();
		assertThat(결과.get().getNickname()).isEqualTo(닉네임);
	}
}
