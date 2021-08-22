package ga.surilaw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrecedentSearchRequestDto {
    String query;
    Integer page;
    String court;
    String date;
    String caseNum;
}
