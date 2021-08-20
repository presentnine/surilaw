package ga.surilaw.service.board;

import ga.surilaw.domain.dto.board.InsertCommentDto;
import ga.surilaw.domain.entity.Comments;

import java.util.Optional;

public interface CommentService {

    Long writeComment(InsertCommentDto insertCommentDto);
    void deleteComment(Long commentId);

}
