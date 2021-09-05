package ga.surilaw.service.bookmark;

import ga.surilaw.domain.entity.Bookmark;
import ga.surilaw.domain.entity.BookmarkedPrecedent;
import java.util.List;

public interface BookmarkService {
    Long createBookmark(String bookmarkName, String userId);
    void addPrecedentToBookmark(Long bookmarkId, int precedentNum, String precedentAlias);
    List<Bookmark> getAllBookmarkOfMember(String userId);
    Bookmark getBookmarkById(Long bookmarkId);
    void changeBookmarkName(Long bookmarkId, String bookmarkName);
    void changePrecedentAlias(Long bookmarkId, int precedentId, String precedentAlias);
    void deleteBookmark(Long bookmarkId);
    void deleteBookmarkedPrecedent(Long bookmarkId, int precedentId);
}
