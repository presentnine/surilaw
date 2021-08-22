package ga.surilaw.repository.board;

import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import ga.surilaw.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class CommentRepositoryTest {

    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Autowired PostInfoRepository postInfoRepository;
    @Autowired CommentRepository commentRepository;


    @Test
    public void insert() throws Exception{
        //given
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        memberRepository.save(member);

        PostInformation postInformation = PostInformation.builder()
                .category(Category.INFO)
                .member(member)
                .postTitle("TestTitle")
                .postContent("Test Content")
                .build();
        postInfoRepository.save(postInformation);

        Comments comments = Comments.builder()
                .posts(postInformation)
                .member(member)
                .commentContent("Test Content")
                .isDeleted(false)
                .isAnswerExists(false)
                .build();

        Comments savedComments = commentRepository.save(comments);

        //when
        Comments findComments = commentRepository.findById(savedComments.getCommentId()).orElse(null);
        System.out.println("comment Id" + findComments.getCommentId());

        //then
        assertThat(findComments.getCommentId()).isEqualTo(findComments.getCommentId());
        assertThat(findComments).isEqualTo(savedComments);
    }


}