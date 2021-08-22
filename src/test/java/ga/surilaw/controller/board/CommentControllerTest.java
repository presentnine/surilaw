package ga.surilaw.controller.board;

import ga.surilaw.domain.dto.board.InsertCommentDto;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import ga.surilaw.repository.board.PostInfoRepository;
import ga.surilaw.repository.member.MemberRepository;
import ga.surilaw.service.board.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@SpringBootTest
@Transactional
@Rollback(value = false)
class CommentControllerTest {

    @Autowired CommentController commentController;

    //for test
    @Autowired EntityManager em;
    @Autowired CommentService commentService;
    @Autowired PostInfoRepository postInfoRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    public void writeComment() throws Exception{

        //given
        Member m = insertSampleMember();
        PostInformation pi = insertSamplePost(m);

        InsertCommentDto insertCommentDto = new InsertCommentDto();
        insertCommentDto.setContent("TestData");
        insertCommentDto.setPostId(pi.getPostId());

        commentController.writeComment(insertCommentDto);

        //when
        //then
    }

    public Member insertSampleMember(){
        Member member = Member.builder()
        .memberId("testMember")
        .memberName("테스트유저")
        .email("SAMPLE@ABC.ABC")
        .passwordHash("1234")
        .memberType('C').build();

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