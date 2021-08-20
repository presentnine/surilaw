package ga.surilaw.service.board;

import ga.surilaw.common.mapper.CommentMapper;
import ga.surilaw.domain.dto.board.InsertCommentDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.repository.board.CommentRepository;
import ga.surilaw.repository.board.PostInfoRepository;
import ga.surilaw.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;
    private final PostInfoRepository postInfoRepository;
    private final MemberRepository memberRepository;

    public Long writeComment(InsertCommentDto insertCommentDto) {

        /*** throw 부분 Exception 처리 필요 ***/
        //Mapping
        Member member = memberRepository.getById(insertCommentDto.getMemberId());
        PostInformation post = postInfoRepository.getById(insertCommentDto.getPostId());
        Comments comments = commentMapper.insertDtotoEntity(insertCommentDto, member, post);

        //루트가 아니면 부모랑도 연결
        if(insertCommentDto.getParentId() != null){
            comments.addParent(
                    commentRepository.getById(insertCommentDto.getParentId()));
        }
        return commentRepository.save(comments).getCommentId();
    }


    public void deleteComment(Long commentId) {
        //실제로 삭제하지는 않음
        Comments comments = commentRepository.findById(commentId).orElseThrow();
        comments.setDeleted();
        return;
    }
}
