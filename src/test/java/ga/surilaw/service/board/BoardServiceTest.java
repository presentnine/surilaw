package ga.surilaw.service.board;

import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import ga.surilaw.repository.board.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    public void write() throws Exception{
        //given
        PostInformation postInfo = PostInformation.builder()
                .memberId(11L)
                .category(Category.INFO)
                .postTitle("TestTitle")
                .postContent("Test Content")
                .build();

        //when
        Long postId = boardService.write(postInfo);

        //then
        PostInformation findPostInfo = boardRepository.getById(postId);
        assertThat(postInfo).isEqualTo(findPostInfo);
    }

    @Test
    public void update() throws Exception{
        //given
        PostInformation postInfo = PostInformation.builder()
                .memberId(11L)
                .category(Category.INFO)
                .postTitle("TestTitle")
                .postContent("Test Content")
                .build();

        Long postId = boardRepository.save(postInfo).getPostId();

        //when
        String newContent = "New Content";

        PostInformation newPostInfo = PostInformation.builder()
                .postId(postId)
                .memberId(11L)
                .category(Category.ASK)
                .postTitle("TestTitle")
                .postContent(newContent)
                .build();

        boardService.update(newPostInfo);

        //then
        PostInformation findPostInfo = boardRepository.getById(postId);

        assertThat(findPostInfo.getCategory()).isEqualTo(Category.ASK);
        assertThat(findPostInfo.getPostContent()).isEqualTo(newContent);
    }

    @Test
    public void delete() throws Exception{
        //given
        PostInformation postInfo = PostInformation.builder()
                .memberId(11L)
                .category(Category.INFO)
                .postTitle("TestTitle")
                .postContent("Test Content")
                .build();

        Long postId = boardRepository.save(postInfo).getPostId();

        //when
        boardService.delete(postId);

        //then
        assertThat(boardRepository.findById(postId)).isEmpty();
    }

}