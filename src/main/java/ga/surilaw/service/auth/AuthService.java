package ga.surilaw.service.auth;

import ga.surilaw.domain.dto.MemberSignUpRequestDto;
import ga.surilaw.domain.dto.MemberLoginRequestDto;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberSignUpRequestDto memberSignUpRequestDto) {
        //아이디(이메일) 중복 확인
        if (memberRepository.existsByEmail(memberSignUpRequestDto.getEmail())) {
            throw new RuntimeException("중복된 아이디입니다");
        }

        //계정 저장
        String passwordHash = passwordEncoder.encode(memberSignUpRequestDto.getPassword());
        Member member = new Member(memberSignUpRequestDto.getEmail(), memberSignUpRequestDto.getMemberName(), passwordHash, 'C');
        memberRepository.save(member);
    }

    @Transactional
    public String login(MemberLoginRequestDto memberLoginRequestDto) {
        Member member = memberRepository.findByEmail(memberLoginRequestDto.getEmail()).orElseThrow(() ->
                new RuntimeException("올바르지 않은 아이디입니다"));

        if(!passwordEncoder.matches(memberLoginRequestDto.getPassword(), member.getPassword()))
            throw new RuntimeException("올바르지 않은 비밀번호입니다");

        String token = jwtTokenProvider.createToken(member);

        return token;
    }
}

