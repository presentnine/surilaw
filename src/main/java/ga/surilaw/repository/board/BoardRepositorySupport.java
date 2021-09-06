package ga.surilaw.repository.board;

import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.dto.board.ReadPostListDto;
import ga.surilaw.domain.dto.board.pagination.FilterDto;
import ga.surilaw.domain.entity.Comments;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositorySupport {
    ReadPostInfoDto readPost(Long postId);
    List<Comments> readComment(Long postId);
    ReadPostListDto getPageList(FilterDto filter, Pageable pageable);
}
