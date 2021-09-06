package ga.surilaw.repository.board;

import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.dto.board.ReadPostListDto;
import ga.surilaw.domain.dto.board.pagination.FilterDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.QPostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import ga.surilaw.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(readPostInfoDto.getPostId()).isEqualTo(postInformation.getPostId());
        assertThat(readPostInfoDto.getUserName()).isEqualTo(member.getMemberName());
    }
    @Test
    public void getPageList() throws Exception{
        //given
        Member a = memberRepository.save(new Member("sample1@abcd.abc","memA","1234",'C'));
        Member b = memberRepository.save(new Member("sample2@abcd.abc","memB","1234",'C'));

        int askSize = 20;
        int infoSize = 10;

        for (int i=0; i<askSize; i++){
            postInfoRepository.save(
                    PostInformation.builder()
                    .category(Category.ASK)
                    .member(a)
                    .postTitle("TestTitle"+i)
                    .postContent("Test Content" +i)
                    .build());
        }

        for (int i=0; i<infoSize; i++){
            postInfoRepository.save(
                    PostInformation.builder()
                            .category(Category.INFO)
                            .member(b)
                            .postTitle("TestTitle"+i)
                            .postContent("Test Content" +i)
                            .build());
        }

        em.flush();
        em.clear();

        FilterDto filterDto = new FilterDto("ASK", null, null, "1");
        Pageable pageable = PageRequest.of(0,5, Sort.Direction.DESC, "postId");

        //when
        ReadPostListDto readPostListDto = boardRepositorySupport.getPageList(filterDto, pageable);

        //then
        assertThat(readPostListDto.getPostList().size()).isEqualTo(5);
        assertThat(readPostListDto.getTotalElements()).isEqualTo(11); //1 + 10~19
        assertThat(readPostListDto.getPostList().get(0).getId()).isGreaterThan(readPostListDto.getPostList().get(1).getId()); //DESC
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
        assertThat(commentLists.size()).isEqualTo(1); //기본자식은 하나
        assertThat(commentLists.get(0).getCommentContent()).isEqualTo(comments.getCommentContent());
        assertThat(commentLists.get(0).getChild().size()).isEqualTo(10); //대댓글 10개
        assertThat(commentLists.get(0).getChild().get(5).getCommentContent()).isEqualTo(comments.getChild().get(5).getCommentContent());
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