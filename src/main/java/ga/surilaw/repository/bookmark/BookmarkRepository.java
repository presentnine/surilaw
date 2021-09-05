package ga.surilaw.repository.bookmark;

import ga.surilaw.domain.entity.Bookmark;
import ga.surilaw.domain.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> getAllByMember(Member member);
}
