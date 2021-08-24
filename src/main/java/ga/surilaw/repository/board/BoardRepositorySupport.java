package ga.surilaw.repository.board;

import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.entity.Comments;

import java.util.List;

public interface BoardRepositorySupport {
    ReadPostInfoDto readPost(Long postId);
    List<Comments> readComment(Long postId);
}
