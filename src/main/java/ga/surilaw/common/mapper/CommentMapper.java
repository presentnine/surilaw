package ga.surilaw.common.mapper;

import ga.surilaw.domain.dto.board.InsertCommentDto;
import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.domain.dto.board.ReadCommentDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default Comments insertDtoToEntity(InsertCommentDto insertCommentDto, Member member, PostInformation postInformation){
        Comments comments = Comments.builder().commentContent(insertCommentDto.getContent()).build();
        comments.changeMember(member);
        postInformation.addComment(comments);
        return comments;
    }

    default ReadCommentDto readEntityToDto(Comments comments){
        if ( comments == null ) {
            return null;
        }

        ReadCommentDto readCommentDto = new ReadCommentDto();
        readCommentDto.setCommentId( comments.getCommentId() );
        if(comments.getCommentContent() != null){
            readCommentDto.setComment(comments.getCommentContent());
        }else {
            readCommentDto.setComment("삭제된 댓글입니다.");
        }

        readCommentDto.setMemberId( commentsMemberMemberId( comments ) );
        readCommentDto.setMemberName( commentsMemberMemberName( comments ) );
        readCommentDto.setCreatedDate(comments.getCreatedDate());

        return readCommentDto;
    }

    private String commentsMemberMemberId(Comments comments) {
        Member member = comments.getMember();
        if ( member == null ) {
            return null;
        }
        return member.getMemberId();
    }

    private String commentsMemberMemberName(Comments comments) {
        Member member = comments.getMember();
        if ( member == null ) {
            return null;
        }
        String memberName = member.getMemberName();
        if ( memberName == null ) {
            return null;
        }
        return memberName;
    }

    default ReadCommentDto readEntityToDtoChild(Comments comments){
        ReadCommentDto commentDto = readEntityToDto(comments);

        if(comments.getChild() != null){
            commentDto.setChilds(
                    comments.getChild().stream()
                            .map(m -> readEntityToDto(m))
                            .collect(Collectors.toList()));
        }
        return commentDto;
    }



}
