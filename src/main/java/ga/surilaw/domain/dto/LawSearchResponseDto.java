package ga.surilaw.domain.dto;

import ga.surilaw.domain.entity.LawBrief;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LawSearchResponseDto {
    int totalCount;
    List<LawBrief> precedentBriefList;
}
