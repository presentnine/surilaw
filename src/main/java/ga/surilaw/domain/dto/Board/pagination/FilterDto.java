package ga.surilaw.domain.dto.board.pagination;

import ga.surilaw.domain.entity.enumType.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterDto {
    Category category;
    String name;
    String title;
    String content;

    public FilterDto(String category, String name, String title, String content){
        this.category = Category.valueOf(category);
        this.name = name;
        this.title = title;
        this.content =content;
    }
}
