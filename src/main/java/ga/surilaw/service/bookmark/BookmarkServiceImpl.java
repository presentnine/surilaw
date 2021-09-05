package ga.surilaw.service.bookmark;

import ga.surilaw.domain.entity.Bookmark;
import ga.surilaw.domain.entity.BookmarkedPrecedent;
import ga.surilaw.domain.entity.BookmarkedPrecedentId;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.repository.bookmark.BookmarkRepository;
import ga.surilaw.repository.bookmark.BookmarkedPrecedentRepository;
import ga.surilaw.repository.member.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    MemberRepository memberRepository;
    BookmarkRepository bookmarkRepository;
    BookmarkedPrecedentRepository bookmarkedPrecedentRepository;

    public BookmarkServiceImpl(MemberRepository memberRepository,
        BookmarkRepository bookmarkRepository,
        BookmarkedPrecedentRepository bookmarkedPrecedentRepository) {
        this.memberRepository = memberRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.bookmarkedPrecedentRepository = bookmarkedPrecedentRepository;
    }

    @Override
    public Long createBookmark(String bookmarkName, String userId) {
        Member member = memberRepository.getById(userId);
        Bookmark bookmark = new Bookmark(bookmarkName, member);
        return bookmarkRepository.save(bookmark).getBookmarkId();
    }

    @Override
    public void addPrecedentToBookmark(Long bookmarkId, int precedentNum, String precedentAlias) {
        Bookmark bookmark = bookmarkRepository.getById(bookmarkId);
        BookmarkedPrecedent bookmarkedPrecedent = new BookmarkedPrecedent(new BookmarkedPrecedentId(bookmark, precedentNum), precedentAlias);
        bookmarkedPrecedentRepository.save(bookmarkedPrecedent);
        bookmark.addPrecedent(bookmarkedPrecedent);
        bookmarkRepository.save(bookmark);
    }

    @Override
    public List<Bookmark> getAllBookmarkOfMember(String userId) {
        Member member = memberRepository.getById(userId);
        return bookmarkRepository.getAllByMember(member);
    }

    @Override
    public Bookmark getBookmarkById(Long bookmarkId) {
        return bookmarkRepository.getById(bookmarkId);
    }

    @Override
    public void changeBookmarkName(Long bookmarkId, String bookmarkName) {
        Bookmark bookmark = bookmarkRepository.getById(bookmarkId);
        bookmark.changeNameTo(bookmarkName);
        bookmarkRepository.save(bookmark);
    }

    @Override
    public void changePrecedentAlias(Long bookmarkId, int precedentId, String precedentAlias) {
        Bookmark bookmark = bookmarkRepository.getById(bookmarkId);
        BookmarkedPrecedentId bookmarkedPrecedentId = new BookmarkedPrecedentId(bookmark, precedentId);
        BookmarkedPrecedent bookmarkedPrecedent = bookmarkedPrecedentRepository.getById(bookmarkedPrecedentId);
        bookmarkedPrecedent.changeAliasTo(precedentAlias);
        bookmarkedPrecedentRepository.save(bookmarkedPrecedent);
    }

    @Override
    public void deleteBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    @Override
    public void deleteBookmarkedPrecedent(Long bookmarkId, int precedentId) {
        Bookmark bookmark = bookmarkRepository.getById(bookmarkId);
        BookmarkedPrecedentId bookmarkedPrecedentId = new BookmarkedPrecedentId(bookmark, precedentId);
        bookmark.getBookmarkedPrecedents().remove(bookmarkedPrecedentRepository.getById(bookmarkedPrecedentId));
    }
}
