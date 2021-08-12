package ga.surilaw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertPostInfoDto {
    private Long postId;
    private Long memberId;
    private String category;
    private String title;
    private String content;
}
