package ga.surilaw.domain.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCommentDto {
    private Long commentId;
    private String comment;
    private String memberId;
    private String memberName;
    private LocalDateTime createdDate;
    private List<ReadCommentDto> childs;
}

