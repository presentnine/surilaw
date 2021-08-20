package ga.surilaw.service.board;

import ga.surilaw.domain.dto.board.InsertCommentDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import ga.surilaw.repository.board.CommentRepository;
import ga.surilaw.repository.board.PostInfoRepository;
import ga.surilaw.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired PostInfoRepository postInfoRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void writeComment() throws Exception{
        //given
        Member m = insertSampleMember();
        PostInformation pi = insertSamplePost(m);
        InsertCommentDto insertCommentDto = new InsertCommentDto(pi.getPostId(),m.getMemberId(), null, "test Content");

        Long savedId = commentService.writeComment(insertCommentDto);

        //when
        Comments findComments = commentRepository.findById(savedId).get();

        //then
        assertThat(findComments.getMember().getMemberId()).isEqualTo(m.getMemberId());
        assertThat(findComments.getPosts().getPostId()).isEqualTo(pi.getPostId());


        System.out.println(findComments.getCommentId());
        System.out.println(findComments.getCommentContent());
        System.out.println(findComments.getPosts().getPostTitle());
        System.out.println(findComments.getMember().getMemberName());
    }

    @Test
    public void writeCommentChild() throws Exception{
        //given

        Member m = insertSampleMember();
        PostInformation pi = insertSamplePost(m);

        Comments parentComments = commentRepository.save(Comments.builder()
                .commentContent("Parent Content")
                .member(m)
                .posts(pi)
                .isDeleted(false)
                .isAnswerExists(false)
                .build());

        InsertCommentDto insertCommentDto = new InsertCommentDto(pi.getPostId(),m.getMemberId(), parentComments.getCommentId(), "test Content");

        Long savedId = commentService.writeComment(insertCommentDto);

        //when
        Comments findComments = commentRepository.findById(savedId).get();

        //then
        assertThat(findComments.getCommentContent()).isEqualTo("test Content");
        assertThat(findComments.getParent().getCommentId()).isEqualTo(parentComments.getCommentId());
    }

    @Test
    public void deleteComment() throws Exception{
        //given
        Member m = insertSampleMember();
        PostInformation pi = insertSamplePost(m);

        Comments parentComments = commentRepository.save(Comments.builder()
                .commentContent("Parent Content")
                .member(m)
                .posts(pi)
                .isDeleted(false)
                .isAnswerExists(false)
                .build());

        InsertCommentDto insertCommentDto = new InsertCommentDto(pi.getPostId(),m.getMemberId(), parentComments.getCommentId(), "test Content");

        Long savedId = commentService.writeComment(insertCommentDto);

        //when
        commentService.deleteComment(savedId);
        Comments childComments = commentRepository.getById(savedId);

        //then
        assertThat(childComments.getCommentContent()).isNull();
        assertThat(childComments.getParent().getCommentId()).isEqualTo(parentComments.getCommentId());

    }

    public Member insertSampleMember(){
        Member member = new Member("sample@abc.abc","테스트","1234","1234",'C');
        return memberRepository.save(member);
    }

    public PostInformation insertSamplePost(Member member){
        PostInformation postInformation = PostInformation.builder()
                .category(Category.INFO)
                .member(member)
                .postTitle("TestTitle")
                .postContent("Test Content")
                .build();

        return postInfoRepository.save(postInformation);
    }

}