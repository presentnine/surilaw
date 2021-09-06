package ga.surilaw.domain.dto.board;

import ga.surilaw.domain.dto.board.pagination.PageInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadPostListDto extends PageInfoDto {
    List<ReadPostShortDto> postList;
    int totalElements;
    String sort;
}
