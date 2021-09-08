package ga.surilaw.common.openapi;

import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchRequestDto;

public interface UriMaker {
    String makePrecedentListUri(PrecedentSearchRequestDto precedentSearchRequestDto);
    String makePrecedentDetailUri(int precedentIdNum);
    String makeLawListUri(LawSearchRequestDto lawSearchRequestDto);
}
