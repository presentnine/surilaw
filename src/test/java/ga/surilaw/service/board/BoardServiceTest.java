package ga.surilaw.service.board;

import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.domain.dto.board.ReadCommentDto;
import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.dto.board.ReadPostListDto;
import ga.surilaw.domain.dto.board.pagination.PageableDto;
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
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardServiceTest {

    @Autowired BoardService boardService;

    @Autowired PostInfoRepository postInfoRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CommentRepository commentRepository;

    @Autowired EntityManager em;

    @Test
    public void write() throws Exception{
        //given
        Member testMember = insertMember();

        InsertPostInfoDto insertPostInfoDto = new InsertPostInfoDto();
        insertPostInfoDto.setMemberId(testMember.getMemberId());
        insertPostInfoDto.setCategory("INFO");
        insertPostInfoDto.setTitle("TestTitle");
        insertPostInfoDto.setContent("Test Content Post");

        //when
        Long postId = boardService.write(insertPostInfoDto);

        //then
        PostInformation findPostInfo = postInfoRepository.getById(postId);
        assertThat(findPostInfo.getPostTitle()).isEqualTo("TestTitle");
        assertThat(findPostInfo.getCategory()).isEqualTo(Category.INFO);
    }

    @Test
    public void update() throws Exception{
        //given
        Member testMember = insertMember();
        PostInformation postInfo = insertPostInformation(testMember);

        em.flush();
        em.clear();

        //when
        String newTitle = "testTitle2";

        InsertPostInfoDto insertPostInfoDto = new InsertPostInfoDto();
        insertPostInfoDto.setMemberId(testMember.getMemberId());
        insertPostInfoDto.setPostId(postInfo.getPostId());
        insertPostInfoDto.setCategory("ASK");
        insertPostInfoDto.setTitle(newTitle);

        Long savedId =  boardService.update(insertPostInfoDto);

        //then
        PostInformation findPostInfo = postInfoRepository.getById(savedId);

        assertThat(findPostInfo.getPostContent()).isEqualTo(postInfo.getPostContent());
        assertThat(findPostInfo.getCategory()).isEqualTo(Category.ASK);
        assertThat(findPostInfo.getPostTitle()).isEqualTo(newTitle);
    }

    @Test
    public void delete() throws Exception{
        //given
        Member testMember = insertMember();
        PostInformation postInfo = insertPostInformation(testMember);

        //when
        boardService.delete(postInfo.getPostId());

        //then
        assertThat(postInfoRepository.findById(postInfo.getPostId())).isEmpty();
    }

    @Test
    public void read() throws Exception{
        //given
        Member member= insertMember();
        Member member2= insertMember();

        PostInformation postInformation = insertPostInformation(member);

        Comments comments1 = insertComments(member, postInformation);
        postInformation.addComment(comments1);
        addChild(member, postInformation, comments1, 10);

        Comments comments2 = insertComments(member, postInformation);
        postInformation.addComment(comments2);
        addChild(member2, postInformation, comments2, 5);

        em.flush();
        em.clear();

        //when
        ReadPostInfoDto read = boardService.read(postInformation.getPostId());

        //then
        assertThat(read.getPostId()).isEqualTo(postInformation.getPostId());
        assertThat(read.getUserName()).isEqualTo(member.getMemberName());

        List<ReadCommentDto> testComments = read.getComments();

        assertThat(testComments.size()).isEqualTo(postInformation.getComments().size());
        assertThat(testComments.get(0).getComment()).isEqualTo(comments1.getCommentContent());

        List<ReadCommentDto> testChilds = testComments.get(1).getChilds();

        assertThat(testChilds.size()).isEqualTo(comments2.getChild().size());
        assertThat(testChilds.get(3).getComment()).isEqualTo(comments2.getChild().get(3).getCommentContent());
    }

    @Test
    public void readList() throws Exception{
        //given
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

        PageableDto pageableDto = new PageableDto();
        pageableDto.setSize(5);
        pageableDto.setPage(1);
        pageableDto.setOrder("DESC");
        pageableDto.setFilter("category:ALL,member:memA,title:1");

        //when
        ReadPostListDto readPostListDto = boardService.readList(pageableDto);

        //then
        assertThat(readPostListDto.getPostList().size()).isEqualTo(5);
        assertThat(readPostListDto.getTotalElements()).isEqualTo(12);
        assertThat(readPostListDto.getPostList().get(0).getId()).isGreaterThan(readPostListDto.getPostList().get(1).getId()); //DESC
    }

    public Member insertMember(){
        Member member = new Member("sample@abc.abc","테스트","1234",'C');
        return memberRepository.save(member);
    }

    public PostInformation insertPostInformation(Member member){
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
}