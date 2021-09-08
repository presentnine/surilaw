package ga.surilaw.service;

import ga.surilaw.domain.entity.Bookmark;
import ga.surilaw.domain.entity.BookmarkedPrecedent;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.bookmark.BookmarkRepository;
import ga.surilaw.repository.member.MemberRepository;
import ga.surilaw.service.bookmark.BookmarkService;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class BookmarkServiceTest {
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookmarkRepository bookmarkRepository;

    @Test
    public void createBookmarkTest(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        String memberId = memberRepository.save(member).getMemberId();

        String bookmarkName = "test";

        bookmarkService.createBookmark(bookmarkName, memberId);

        List<Bookmark> testBookmarkList = bookmarkRepository.getAllByMember(member);

        assertThat(testBookmarkList.size()).isEqualTo(1);
        assertThat(testBookmarkList.get(0).getBookmarkName()).isEqualTo("test");
    }

    @Test
    public void addPrecedentToBookmarkTest(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        String memberId = memberRepository.save(member).getMemberId();

        String bookmarkName = "test";

        Long bookmarkId = bookmarkService.createBookmark(bookmarkName, memberId);

        bookmarkService.addPrecedentToBookmark(bookmarkId, 1234, "test");

        Bookmark bookmark = bookmarkRepository.getById(bookmarkId);
        List<BookmarkedPrecedent> bookmarkList = bookmark.getBookmarkedPrecedents();

        assertThat(bookmarkList.size()).isEqualTo(1);
        assertThat(bookmarkList.get(0).getBookmarkedPrecedentId().getPrecedentNum()).isEqualTo(1234);
        assertThat(bookmarkList.get(0).getPrecedentAlias()).isEqualTo("test");
    }

    @Test
    public void getAllBookmarkOfMemberTest(){
        Member member1 = new Member("sample1@abc.abc","테스트","1234",'C');
        String userId1 = memberRepository.save(member1).getMemberId();

        Member member2 = new Member("sample2@abc.abc","테스트","1234",'C');
        String userID2 = memberRepository.save(member2).getMemberId();

        Bookmark testBookmark1 = new Bookmark("test", member1);
        bookmarkRepository.save(testBookmark1);
        Bookmark testBookmark2 = new Bookmark("qqqq", member1);
        bookmarkRepository.save(testBookmark2);
        Bookmark testBookmark3 = new Bookmark("wwww", member2);
        bookmarkRepository.save(testBookmark3);

        List<Bookmark> bookmarkList1 = bookmarkService.getAllBookmarkOfMember(userId1);
        List<Bookmark> bookmarkList2 = bookmarkService.getAllBookmarkOfMember(userID2);

        assertThat(bookmarkList1.size()).isEqualTo(2);
        assertThat(bookmarkList2.size()).isEqualTo(1);
    }

    @Test
    public void getBookmarkByIdTest(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        String memberId = memberRepository.save(member).getMemberId();

        String bookmarkName = "test";

        Long bookmarkId = bookmarkService.createBookmark(bookmarkName, memberId);

        Bookmark bookmark = bookmarkService.getBookmarkById(bookmarkId);

        assertThat(bookmark.getBookmarkId()).isEqualTo(bookmarkId);
        assertThat(bookmark.getBookmarkName()).isEqualTo("test");
    }

    @Test
    public void changeBookmarkNameTest(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        String memberId = memberRepository.save(member).getMemberId();

        String bookmarkName = "test";

        Long bookmarkId = bookmarkService.createBookmark(bookmarkName, memberId);
        bookmarkService.changeBookmarkName(bookmarkId, "modified");

        Bookmark modified = bookmarkRepository.getById(bookmarkId);
        assertThat(modified.getBookmarkId()).isEqualTo(bookmarkId);
        assertThat(modified.getBookmarkName()).isEqualTo("modified");
    }

    @Test
    public void changePrecedentAliasTest(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        String memberId = memberRepository.save(member).getMemberId();

        String bookmarkName = "test";

        Long bookmarkId = bookmarkService.createBookmark(bookmarkName, memberId);

        bookmarkService.addPrecedentToBookmark(bookmarkId, 1234, "test");

        bookmarkService.changePrecedentAlias(bookmarkId, 1234, "modified");
        Bookmark bookmark = bookmarkRepository.getById(bookmarkId);
        assertThat(bookmark.getBookmarkedPrecedents().get(0).getPrecedentAlias()).isEqualTo("modified");
    }

    @Test
    public void deleteBookmarkTest(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        String memberId = memberRepository.save(member).getMemberId();

        String bookmarkName = "test";

        Long bookmarkId = bookmarkService.createBookmark(bookmarkName, memberId);

        bookmarkService.deleteBookmark(bookmarkId);

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElse(null);
        assertThat(bookmark).isEqualTo(null);
    }

    @Test
    public void deleteBookmarkedPrecedentTest(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        String memberId = memberRepository.save(member).getMemberId();

        String bookmarkName = "test";

        Long bookmarkId = bookmarkService.createBookmark(bookmarkName, memberId);

        bookmarkService.addPrecedentToBookmark(bookmarkId, 1234, "test");

        bookmarkService.deleteBookmarkedPrecedent(bookmarkId, 1234);

        Bookmark bookmark = bookmarkRepository.getById(bookmarkId);
        List<BookmarkedPrecedent> bookmarkList = bookmark.getBookmarkedPrecedents();

        assertThat(bookmarkList.size()).isEqualTo(0);
    }
}
