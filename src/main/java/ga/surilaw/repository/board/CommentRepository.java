package ga.surilaw.repository.board;

import ga.surilaw.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
}
