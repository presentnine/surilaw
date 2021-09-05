package ga.surilaw.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_num")
    Long bookmarkId;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    Member member;

    @Column(name = "folder_name")
    String bookmarkName;

    @OneToMany(mappedBy = "bookmarkedPrecedentId.bookmark", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<BookmarkedPrecedent> bookmarkedPrecedents;

    public Bookmark(String bookmarkName, Member member) {
        this.bookmarkName = bookmarkName;
        this.member = member;
    }

    public void addPrecedent(BookmarkedPrecedent precedent){
        if (bookmarkedPrecedents == null) {
            bookmarkedPrecedents = new ArrayList<BookmarkedPrecedent>();
        }
        bookmarkedPrecedents.add(precedent);
    }

    public void changeNameTo(String bookmarkName){
        this.bookmarkName = bookmarkName;
    }

    public Long getBookmarkId() {
        return bookmarkId;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public List<BookmarkedPrecedent> getBookmarkedPrecedents() {
        return bookmarkedPrecedents;
    }
}
