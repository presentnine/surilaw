package ga.surilaw.domain.dto.board;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertCommentDto {
    public Long postId;
    public String memberId;
    public Long parentId;
    public String content;
}
