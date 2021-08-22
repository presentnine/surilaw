package ga.surilaw.service.board;

import ga.surilaw.domain.entity.PostInformation;

public interface BoardService {
    Long write(PostInformation postInformation);
    Long update(PostInformation postInformation);
    void delete(Long postId);
}
