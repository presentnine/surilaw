package ga.surilaw.service.board;

import ga.surilaw.common.mapper.CommentMapper;
import ga.surilaw.common.mapper.PostInfoMapper;
import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.domain.dto.board.ReadCommentDto;
import ga.surilaw.domain.dto.board.ReadPostInfoDto;
import ga.surilaw.domain.dto.board.ReadPostListDto;
import ga.surilaw.domain.dto.board.pagination.FilterDto;
import ga.surilaw.domain.dto.board.pagination.PageableDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.repository.board.BoardRepositorySupport;
import ga.surilaw.repository.board.CommentRepository;
import ga.surilaw.repository.board.PostInfoRepository;
import ga.surilaw.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final PostInfoRepository postInfoRepository;
    private final MemberRepository memberRepository;
    private final PostInfoMapper postInfoMapper;

    private final BoardRepositorySupport boardRepositorySupport;
    private final CommentMapper commentsMapper;

    //custom

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

    public ReadPostInfoDto read(Long postId){

        ReadPostInfoDto readPostInfoDto = boardRepositorySupport.readPost(postId);

        //일단 다 땡겨오기
        List<Comments> comments = boardRepositorySupport.readComment(postId);

        //이후 매핑
        List<ReadCommentDto> readCommentDto  = comments.stream().map(
                m-> commentsMapper.readEntityToDtoChild(m))
                .collect(Collectors.toList());

        readPostInfoDto.setComments(readCommentDto);
        System.out.println(readPostInfoDto);
        return readPostInfoDto;
    }

    public ReadPostListDto readList(PageableDto pageableDto){

        //Pageable Setting
        Pageable pageable = PageRequest.of(pageableDto.getPage(),
                pageableDto.getSize(),
                getSortOrders(pageableDto.getOrder(), pageableDto.getSort()));

        //Filter Setting
        if(StringUtils.hasLength(pageableDto.getFilter())){
            pageableDto.setFilters();
        }

        return boardRepositorySupport.getPageList(pageableDto.getFilters(), pageable);
    }

    private Sort getSortOrders(String order,String sort){
        if(StringUtils.hasLength(order)){
            if(order.toUpperCase(Locale.ROOT).equals("DESC")){
                return Sort.by(Sort.Direction.DESC, sort);
            }
        }
        return Sort.by(Sort.Direction.ASC, sort);
    }
}
