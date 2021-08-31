package ga.surilaw.controller.auth;

import ga.surilaw.common.response.AuthException;
import ga.surilaw.domain.dto.MemberLoginRequestDto;
import ga.surilaw.domain.dto.MemberSignUpRequestDto;
import ga.surilaw.domain.dto.MemberSignUpResponseDto;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
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

        AuthException error = org.junit.jupiter.api.Assertions.assertThrows(AuthException.class, () ->
        {
            authController.signUp(duplicateMember);
        });

        String errorMessage = error.getMessage();

        org.junit.jupiter.api.Assertions.assertEquals("중복된 아이디입니다", errorMessage);

    }

    @Test
    void login() {
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authController.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto("1234@naver.com", "1234");
        authController.login(memberLoginRequestDto);
    }

    @Test
    void loginFailedByWrongId() {
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authController.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto("123@naver.com", "1234");

        AuthException error = org.junit.jupiter.api.Assertions.assertThrows(AuthException.class, () ->
        {
            authController.login(memberLoginRequestDto);
        });

        String errorMessage = error.getMessage();

        org.junit.jupiter.api.Assertions.assertEquals("올바르지 않은 아이디입니다", errorMessage);
    }

    @Test
    void loginFailedByWrongPassword() {
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authController.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto("1234@naver.com", "123");

        AuthException error = org.junit.jupiter.api.Assertions.assertThrows(AuthException.class, () ->
        {
            authController.login(memberLoginRequestDto);
        });

        String errorMessage = error.getMessage();

        org.junit.jupiter.api.Assertions.assertEquals("올바르지 않은 비밀번호입니다", errorMessage);
    }
}