package ga.surilaw.repository.board;

import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import ga.surilaw.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardRepositorySupportImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired PostInfoRepository postInfoRepository;
    @Autowired CommentRepository commentRepository;

    @Autowired BoardRepositorySupport boardRepositorySupport;
    @Autowired EntityManager em;
    @Test
    public void readPost(){
        //given
        Member member = insertMember();
        PostInformation postInformation = insertPostinformation(member);
        em.flush();
        em.clear();

        //when
        ReadPostInfoDto readPostInfoDto = boardRepositorySupport.readPost(postInformation.getPostId());

        //then
        Assertions.assertThat(readPostInfoDto.getPostId()).isEqualTo(postInformation.getPostId());
        Assertions.assertThat(readPostInfoDto.getUserName()).isEqualTo(member.getMemberName());
    }
    
    @Test
    public void readComment() throws Exception{
        //given
        Member member = insertMember();
        PostInformation postInformation = insertPostinformation(member);
        Comments comments = insertComments(member,postInformation);

        addChild(member, postInformation, comments, 10);

        //when
        List<Comments> commentLists = boardRepositorySupport.readComment(postInformation.getPostId());

        //then
        Assertions.assertThat(commentLists.size()).isEqualTo(1); //기본자식은 하나
        Assertions.assertThat(commentLists.get(0).getCommentContent()).isEqualTo(comments.getCommentContent());
        Assertions.assertThat(commentLists.get(0).getChild().size()).isEqualTo(10); //대댓글 10개
        Assertions.assertThat(commentLists.get(0).getChild().get(5).getCommentContent()).isEqualTo(comments.getChild().get(5).getCommentContent());
    }

    public void addChild(Member member, PostInformation postInformation, Comments parent, int times){
        for(int i=0; i<times; i++){
            Comments c =   Comments.builder()
                    .posts(postInformation)
                    .member(member)
                    .commentContent("Test Content Child"+i)
                    .isDeleted(false)
                    .isAnswerExists(false)
                    .build();
            c.addParent(parent);
            commentRepository.save(c);
        }
    }
    
    public Member insertMember(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        return memberRepository.save(member);
    }
    
    public PostInformation insertPostinformation(Member member){
        PostInformation postInformation = PostInformation.builder()
                .category(Category.INFO)
                .member(member)
                .postTitle("TestTitle")
                .postContent("Test Content")
                .build();
        return postInfoRepository.save(postInformation);
    }

    public Comments insertComments(Member member, PostInformation postInformation){
        Comments comments = Comments.builder()
                .posts(postInformation)
                .member(member)
                .commentContent("Test Content")
                .isDeleted(false)
                .isAnswerExists(false)
                .build();

        return commentRepository.save(comments);
    }


}