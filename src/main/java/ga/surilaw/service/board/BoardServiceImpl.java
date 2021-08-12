package ga.surilaw.service.board;

import ga.surilaw.entity.PostInformation;
import ga.surilaw.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    public Long write(PostInformation postInformation) {
        return boardRepository.save(postInformation).getPostId();
    }

    public Long update(PostInformation postInformation) {
        return boardRepository.save(postInformation).getPostId();
    }

    public void delete(Long postId) {
        boardRepository.deleteById(postId);
    }
}
