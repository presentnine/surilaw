package ga.surilaw.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LawBrief {
    int idNum;
    String current;
    String name;
    String abbr;
    int emitDate;
    String category;
    int effectDate;
}
