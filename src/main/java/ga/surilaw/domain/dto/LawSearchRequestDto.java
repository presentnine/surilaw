package ga.surilaw.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LawSearchRequestDto {
    String query;
    String date;
    String efYd;
    String ancYd;
}
