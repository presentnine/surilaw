package ga.surilaw.domain.dto;

import ga.surilaw.domain.entity.PrecedentBrief;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrecedentSearchResponseDto {
    int totalCount;
    List<PrecedentBrief> precedentBriefList;
}
