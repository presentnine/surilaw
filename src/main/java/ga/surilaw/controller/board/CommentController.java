package ga.surilaw.controller.board;

import ga.surilaw.domain.dto.board.InsertCommentDto;
import ga.surilaw.service.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/board/comment/insert")
    public void writeComment(@RequestBody InsertCommentDto insertCommentDto){

        //memberId 넣어줘야함.
        String memberId = "testMember";
        insertCommentDto.setMemberId(memberId);
        commentService.writeComment(insertCommentDto);
    }

    @DeleteMapping("/api/board/comment/update/{commentId}")
    public void deleteComment(@PathVariable(name = "commentId") Long commentId){
        commentService.deleteComment(commentId);
    }

}
