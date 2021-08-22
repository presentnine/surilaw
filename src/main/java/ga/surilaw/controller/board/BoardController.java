package ga.surilaw.controller.board;

import ga.surilaw.common.mapper.PostInfoMapper;
import ga.surilaw.domain.dto.InsertPostInfoDto;
import ga.surilaw.domain.entity.PostInformation;
import ga.surilaw.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final PostInfoMapper postInfoMapper;
    private final BoardService boardService;

    @PostMapping("/api/board/insert")
    public void writePost(@RequestBody InsertPostInfoDto insertPostInfoDto){
        //memberId 넣어줘야함.
        PostInformation postInformation =postInfoMapper.insertDtoToEntity(insertPostInfoDto);
        boardService.write(postInformation);
    }

    @PutMapping("/api/board/update")
    public void updatePost(@RequestBody InsertPostInfoDto insertPostInfoDto){
        //memberId 넣어줘야함.
        PostInformation postInformation =postInfoMapper.insertDtoToEntity(insertPostInfoDto);
        boardService.update(postInformation);
    }

    @DeleteMapping("/api/board/delete/{postId}")
    public void deletePost(@PathVariable(name = "postId") Long postId){
        boardService.delete(postId);
    }
}
