package ga.surilaw.service.auth;

import ga.surilaw.domain.dto.MemberDTO;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void singUp(MemberDTO memberDTO) {
        //아이디(이메일) 중복 확인
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new RuntimeException("중복된 아이디입니다");
        }

        //계정 저장
        String passwordHash = passwordEncoder.encode(memberDTO.getPassword());
        Member member = new Member(memberDTO.getEmail(), memberDTO.getMemberName(), passwordHash, 'C');
        memberRepository.save(member);
    }
}*/
//Auth 에러로 주석처리 - 엄현식
