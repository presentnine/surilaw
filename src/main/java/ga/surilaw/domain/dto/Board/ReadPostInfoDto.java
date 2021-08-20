package ga.surilaw.domain.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadPostInfoDto {
    private Long postId;
    private String memberName;
    private String category;
    private String title;
    private String content;
}
