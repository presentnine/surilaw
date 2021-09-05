package ga.surilaw.repository;

import ga.surilaw.domain.entity.Bookmark;
import ga.surilaw.domain.entity.BookmarkedPrecedent;
import ga.surilaw.domain.entity.BookmarkedPrecedentId;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.bookmark.BookmarkRepository;
import ga.surilaw.repository.bookmark.BookmarkedPrecedentRepository;
import ga.surilaw.repository.member.MemberRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookmarkedPrecedentRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    BookmarkedPrecedentRepository bookmarkedPrecedentRepository;

    @Test
    public void createAndRead(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        memberRepository.save(member);

        Bookmark bookmark = new Bookmark("test", member);
        bookmarkRepository.save(bookmark);

        BookmarkedPrecedent testPrecedent = new BookmarkedPrecedent(new BookmarkedPrecedentId(bookmark, 1234), "test");
        BookmarkedPrecedent given = bookmarkedPrecedentRepository.save(testPrecedent);
        BookmarkedPrecedent saved = bookmarkedPrecedentRepository.getById(new BookmarkedPrecedentId(given.getBookmarkedPrecedentId().getBookmark(), given.getBookmarkedPrecedentId().getPrecedentNum()));

        assertThat(given).isEqualTo(saved);
    }

    @Test
    public void update(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        memberRepository.save(member);

        Bookmark bookmark = new Bookmark("test", member);
        bookmarkRepository.save(bookmark);

        BookmarkedPrecedent testPrecedent = new BookmarkedPrecedent(new BookmarkedPrecedentId(bookmark, 1234), "test");
        BookmarkedPrecedent given = bookmarkedPrecedentRepository.save(testPrecedent);
        BookmarkedPrecedent saved = bookmarkedPrecedentRepository.getById(new BookmarkedPrecedentId(given.getBookmarkedPrecedentId().getBookmark(), given.getBookmarkedPrecedentId().getPrecedentNum()));

        saved.changeAliasTo("modified");
        em.flush();
        em.clear();

        BookmarkedPrecedent modified = bookmarkedPrecedentRepository.getById(new BookmarkedPrecedentId(given.getBookmarkedPrecedentId().getBookmark(), given.getBookmarkedPrecedentId().getPrecedentNum()));

        assertThat(modified.getPrecedentAlias()).isEqualTo("modified");
    }

    @Test
    public void delete(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        memberRepository.save(member);

        Bookmark bookmark = new Bookmark("test", member);
        bookmarkRepository.save(bookmark);

        BookmarkedPrecedent testPrecedent = new BookmarkedPrecedent(new BookmarkedPrecedentId(bookmark, 1234), "test");
        BookmarkedPrecedent given = bookmarkedPrecedentRepository.save(testPrecedent);
        BookmarkedPrecedent saved = bookmarkedPrecedentRepository.getById(new BookmarkedPrecedentId(given.getBookmarkedPrecedentId().getBookmark(), given.getBookmarkedPrecedentId().getPrecedentNum()));

        bookmarkedPrecedentRepository.delete(saved);
        BookmarkedPrecedent deleted = bookmarkedPrecedentRepository.findById(new BookmarkedPrecedentId(given.getBookmarkedPrecedentId().getBookmark(), given.getBookmarkedPrecedentId().getPrecedentNum())).orElse(null);
        assertThat(deleted).isEqualTo(null);
    }
}
