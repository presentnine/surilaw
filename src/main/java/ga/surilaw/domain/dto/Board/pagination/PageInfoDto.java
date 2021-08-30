package ga.surilaw.domain.dto.board.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoDto {
    int totalPages;
    int page;
    int size;
    String order;
}
