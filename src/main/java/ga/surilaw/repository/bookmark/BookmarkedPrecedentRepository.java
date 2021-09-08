package ga.surilaw.repository.bookmark;

import ga.surilaw.domain.entity.BookmarkedPrecedent;
import ga.surilaw.domain.entity.BookmarkedPrecedentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkedPrecedentRepository extends JpaRepository <BookmarkedPrecedent, BookmarkedPrecedentId> {

}
