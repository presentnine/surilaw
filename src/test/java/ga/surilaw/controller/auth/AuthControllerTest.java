package ga.surilaw.controller.auth;

import ga.surilaw.domain.dto.MemberSignUpRequestDto;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@EnableJpaAuditing
@Transactional
//@Rollback(value = false)
class AuthControllerTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthController authController;

    @Test
    public void signUp() {
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authController.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findByEmail(memberSignUpRequestDto.getEmail()).get();
        Assertions.assertThat(findMember.getMemberName()).isEqualTo(memberSignUpRequestDto.getMemberName());
    }

    @Test
    public void signUpFailed() throws Exception{
        MemberSignUpRequestDto member = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authController.signUp(member);

        em.flush();
        em.clear();

        MemberSignUpRequestDto duplicateMember = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");

        RuntimeException error = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
        {
            authController.signUp(duplicateMember);
        });

        String errorMessage = error.getMessage();

        org.junit.jupiter.api.Assertions.assertEquals("중복된 아이디입니다", errorMessage);

    }
}