package ga.surilaw.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PrecedentBrief {
    int idNum;
    String caseName;
    String caseNum;
    String date;
    String court;
}
