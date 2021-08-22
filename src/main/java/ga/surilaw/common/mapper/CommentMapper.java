package ga.surilaw.common.mapper;

import ga.surilaw.domain.dto.board.InsertCommentDto;
import ga.surilaw.domain.entity.Comments;
import ga.surilaw.domain.entity.Member;
import ga.surilaw.domain.entity.PostInformation;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default Comments insertDtotoEntity(InsertCommentDto insertCommentDto, Member member, PostInformation postInformation){
        Comments comments = Comments.builder().commentContent(insertCommentDto.getContent()).build();
        comments.changeMember(member);
        postInformation.addComment(comments);
        return comments;
    }

}
