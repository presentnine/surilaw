package ga.surilaw.service.board;

import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.domain.entity.PostInformation;

public interface BoardService {
    Long write(InsertPostInfoDto insertPostInfoDto);
    Long update(InsertPostInfoDto insertPostInfoDto);
    void delete(Long postId);
    PostInformation read(Long postId);
}
