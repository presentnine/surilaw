package ga.surilaw.service.board;

import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.domain.entity.enumType.Category;
import ga.surilaw.repository.board.PostInfoRepository;
import ga.surilaw.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardServiceTest {

    @Autowired BoardService boardService;

    @Autowired
    PostInfoRepository postInfoRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void write() throws Exception{
        //given
        Member testMember = insertSampleMember();

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
        Member testMember = insertSampleMember();

        PostInformation postInfo = postInfoRepository.save(PostInformation.builder()
                .postTitle("TestTitle")
                .postContent("Test Content Post")
                .member(testMember)
                .category(Category.INFO)
                .build());

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
        Member testMember = insertSampleMember();

        PostInformation postInfo = postInfoRepository.save(PostInformation.builder()
                .postTitle("TestTitle")
                .postContent("Test Content Post")
                .member(testMember)
                .category(Category.INFO)
                .build());

        em.flush();
        em.clear();


        //when
        boardService.delete(postInfo.getPostId());

        //then
        assertThat(postInfoRepository.findById(postInfo.getPostId())).isEmpty();
    }

    @Test
    public void read() throws Exception{
        //given
        Member testMember = insertSampleMember();

        PostInformation postInfo = postInfoRepository.save(PostInformation.builder()
                .postTitle("TestTitle")
                .postContent("Test Content Post")
                .member(testMember)
                .category(Category.INFO)
                .build());

        em.flush();
        em.clear();

        //when
        PostInformation findPost = boardService.read(postInfo.getPostId());

        //then
        assertThat(findPost.getPostContent().equals(postInfo.getPostContent()));
        assertThat(findPost.getMember().getMemberId().equals("testMember"));
    }

    public Member insertSampleMember(){
        Member member = new Member("sample@abc.abc","테스트","1234","1234",'C');
        return memberRepository.save(member);
    }
}