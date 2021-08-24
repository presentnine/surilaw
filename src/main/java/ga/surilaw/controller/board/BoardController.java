package ga.surilaw.controller.board;

import ga.surilaw.common.mapper.PostInfoMapper;
import ga.surilaw.domain.dto.board.InsertPostInfoDto;
import ga.surilaw.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/board/insert")
    public void writePost(@RequestBody InsertPostInfoDto insertPostInfoDto){

        //memberId 넣어줘야함.
        String memberId = "testMember";
        insertPostInfoDto.setMemberId(memberId);
        boardService.write(insertPostInfoDto);
    }

    @PutMapping("/api/board/update")
    public void updatePost(@RequestBody InsertPostInfoDto insertPostInfoDto){

        //memberId 넣어줘야함.
        String memberId = "testMember";
        insertPostInfoDto.setMemberId(memberId);
        boardService.write(insertPostInfoDto);
    }

    @DeleteMapping("/api/board/delete/{postId}")
    public void deletePost(@PathVariable(name = "postId") Long postId){
        boardService.delete(postId);
    }


    @GetMapping("/api/board/{postId}")
    public ResponseEntity<?> readPost(@PathVariable(name = "postId") Long postId){
        //mapper + memberId -> memberName? or 반정규화
        return ResponseEntity.ok(boardService.read(postId));
    }
}
