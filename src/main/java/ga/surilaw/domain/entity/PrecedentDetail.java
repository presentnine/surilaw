package ga.surilaw.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PrecedentDetail {
    int idNum;
    String caseName;
    String caseNum;
    int sentenceDate;
    String court;
    String caseCategoryName;
    String judgeCategory;
    String judgeHolding;
    String judgeBrief;
    String refText;
    String refPrecedent;
    String judgeContent;
}
