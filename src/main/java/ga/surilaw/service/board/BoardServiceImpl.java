package ga.surilaw.service.board;

import ga.surilaw.common.mapper.PostInfoMapper;
import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.repository.board.PostInfoRepository;
import ga.surilaw.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final PostInfoRepository postInfoRepository;
    private final MemberRepository memberRepository;
    private final PostInfoMapper postInfoMapper;

    @Override
    public Long write(InsertPostInfoDto insertPostInfoDto) {

        //mapping
        PostInformation posts = postInfoMapper.insertDtoToEntity(insertPostInfoDto);
        Member member = memberRepository.getById(insertPostInfoDto.getMemberId());
        posts.addMember(member);

        return postInfoRepository.save(posts).getPostId();
    }

    @Override
    public Long update(InsertPostInfoDto insertPostInfoDto) {

        //mapping
        PostInformation posts = postInfoRepository.findById(insertPostInfoDto.getPostId()).orElseThrow();
        return postInfoRepository.save(postInfoMapper.updateDtoToEntity(insertPostInfoDto,posts)).getPostId();
    }

    public void delete(Long postId) {
        postInfoRepository.deleteById(postId);
    }

    public PostInformation read(Long postId){
        return postInfoRepository.findById(postId).orElseThrow();
    }
}
