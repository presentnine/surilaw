package ga.surilaw.domain.dto.board;

import ga.surilaw.domain.entity.enumType.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadPostInfoDto {
    private Long postId;
    private String userId;
    private String userName;
    private String type;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private List<ReadCommentDto> comments;

    public ReadPostInfoDto (Long postId, String userId,String userName,
                            Category category, String title, String content, LocalDateTime createdDate){
        this.postId = postId;
        this.userId = userId;
        this.userName = userName;
        this.type = category.getValue();
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }
}
