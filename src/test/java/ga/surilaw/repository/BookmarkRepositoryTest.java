package ga.surilaw.repository;

import ga.surilaw.domain.entity.Bookmark;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.bookmark.BookmarkRepository;
import ga.surilaw.repository.member.MemberRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookmarkRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;

    @Test
    public void createAndRead(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        memberRepository.save(member);

        Bookmark testBookmark = new Bookmark("test", member);
        Bookmark given = bookmarkRepository.save(testBookmark);
        Bookmark saved = bookmarkRepository.getById(given.getBookmarkId());

        assertThat(given).isEqualTo(saved);
    }

    @Test
    public void update(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        memberRepository.save(member);

        Bookmark testBookmark = new Bookmark("test", member);
        Bookmark given = bookmarkRepository.save(testBookmark);
        Bookmark saved = bookmarkRepository.getById(given.getBookmarkId());

        saved.changeNameTo("modified");
        em.flush();
        em.clear();

        Bookmark updated = bookmarkRepository.getById(given.getBookmarkId());
        assertThat(updated.getBookmarkName()).isEqualTo("modified");
    }

    @Test
    public void delete(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        memberRepository.save(member);

        Bookmark testBookmark = new Bookmark("test", member);
        Bookmark given = bookmarkRepository.save(testBookmark);
        Bookmark saved = bookmarkRepository.getById(given.getBookmarkId());

        bookmarkRepository.delete(saved);
        Bookmark deleted = bookmarkRepository.findById(given.getBookmarkId()).orElse(null);
        assertThat(deleted).isEqualTo(null);
    }

    @Test
    public void getByMember(){
        Member member1 = new Member("sample1@abc.abc","테스트","1234",'C');
        memberRepository.save(member1);

        Member member2 = new Member("sample2@abc.abc","테스트","1234",'C');
        memberRepository.save(member2);

        Bookmark testBookmark1 = new Bookmark("test", member1);
        bookmarkRepository.save(testBookmark1);
        Bookmark testBookmark2 = new Bookmark("qqqq", member1);
        bookmarkRepository.save(testBookmark2);
        Bookmark testBookmark3 = new Bookmark("wwww", member2);
        bookmarkRepository.save(testBookmark3);

        List<Bookmark> bookmarksOfMember1 = bookmarkRepository.getAllByMember(member1);
        List<Bookmark> bookmarksOfMember2 = bookmarkRepository.getAllByMember(member2);

        assertThat(bookmarksOfMember1.size()).isEqualTo(2);
        assertThat(bookmarksOfMember2.size()).isEqualTo(1);
    }
}
