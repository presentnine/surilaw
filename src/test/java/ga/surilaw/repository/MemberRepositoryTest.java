package ga.surilaw.repository;

import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@EnableJpaAuditing
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save() {
        Member member = new Member("asdf@naver.com", "홍길동", "???", 'C');

        Member saveMember = memberRepository.save(member);
        Optional<Member> result = memberRepository.findById(saveMember.getMemberId());
        Member findMember = result.get();

        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    public void update(){
        Member member = new Member("asdf@naver.com", "홍길동", "???", 'C');
        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId()).get();

        findMember.setMemberName("아무개");

        em.flush();
        em.clear();

        Member updateMember = memberRepository.findById(saveMember.getMemberId()).get();

        assertThat(updateMember.getMemberName()).isEqualTo("아무개");
    }

    @Test
    public void delete() {
        Member member = new Member("asdf@naver.com", "홍길동", "???", 'C');
        Member saveMember = memberRepository.save(member);

        em.flush();
        em.clear();

        memberRepository.deleteById(saveMember.getMemberId());

        em.flush();
        em.clear();

        Optional<Member> result = memberRepository.findById(saveMember.getMemberId());

        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void findByEmail(){
        Member member = new Member("asdf@naver.com", "홍길동", "???", 'C');
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findByEmail("asdf@naver.com").orElse(null);

        assertThat(findMember.getMemberName()).isEqualTo("홍길동");
    }

    @Test
    public void existsByEmail() {


    }
}