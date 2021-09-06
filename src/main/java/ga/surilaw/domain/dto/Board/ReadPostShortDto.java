package ga.surilaw.domain.dto.board;

import com.querydsl.jpa.impl.JPAUtil;
import ga.surilaw.domain.entity.enumType.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadPostShortDto {
    public Long id;
    public String memberName;
    public String contentTitle;
    public Category category;
}
