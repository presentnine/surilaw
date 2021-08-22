package ga.surilaw.service.auth;

import ga.surilaw.domain.dto.MemberLoginRequestDto;
import ga.surilaw.domain.dto.MemberSignUpRequestDto;
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
import java.util.Optional;

@SpringBootTest
@EnableJpaAuditing
@Transactional
class AuthServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    AuthService authService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void singUp() throws Exception{
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authService.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findByEmail(memberSignUpRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("맞는 계정 없음"));
        Assertions.assertThat(findMember.getMemberName()).isEqualTo(memberSignUpRequestDto.getMemberName());
    }

    @Test
    public void signUpFailed() throws Exception{
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authService.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        MemberSignUpRequestDto duplicateMember = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");

        RuntimeException error = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
        {
            authService.signUp(duplicateMember);
        });

        String errorMessage = error.getMessage();

        org.junit.jupiter.api.Assertions.assertEquals("중복된 아이디입니다", errorMessage);
    }


    @Test
    void login() {
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authService.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto("1234@naver.com", "1234");
        authService.login(memberLoginRequestDto);
    }

    @Test
    void loginFailedByWrongId() {
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authService.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto("123@naver.com", "1234");

        RuntimeException error = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
        {
            authService.login(memberLoginRequestDto);
        });

        String errorMessage = error.getMessage();

        org.junit.jupiter.api.Assertions.assertEquals("올바르지 않은 아이디입니다", errorMessage);
    }

    @Test
    void loginFailedByWrongPassword() {
        MemberSignUpRequestDto memberSignUpRequestDto = new MemberSignUpRequestDto("1234@naver.com", "김현구", "1234");
        authService.signUp(memberSignUpRequestDto);

        em.flush();
        em.clear();

        MemberLoginRequestDto memberLoginRequestDto = new MemberLoginRequestDto("1234@naver.com", "123");

        RuntimeException error = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () ->
        {
            authService.login(memberLoginRequestDto);
        });

        String errorMessage = error.getMessage();

        org.junit.jupiter.api.Assertions.assertEquals("올바르지 않은 비밀번호입니다", errorMessage);
    }
}