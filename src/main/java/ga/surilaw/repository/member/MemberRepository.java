package ga.surilaw.repository.member;

import ga.surilaw.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    //아이디 찾기
    Optional<Member> findByEmail(String email);

    //아이디 중복 방지
    boolean existsByEmail(String email);
}
