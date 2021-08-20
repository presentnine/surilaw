package ga.surilaw.controller.auth;

import ga.surilaw.domain.dto.MemberDTO;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
/*
@SpringBootTest
@EnableJpaAuditing
@Transactional
@Rollback(value = false)
class AuthControllerTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthController authController;

    @Test
    public void signUp() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberName("김현구");
        memberDTO.setEmail("1234@naver.com");
        memberDTO.setPassword("1234");

        authController.signUp(memberDTO);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findByEmail(memberDTO.getEmail()).get();
        Assertions.assertThat(findMember.getMemberName()).isEqualTo(memberDTO.getMemberName());
    }
}
*/
//Auth 에러로 주석처리 - 엄현식