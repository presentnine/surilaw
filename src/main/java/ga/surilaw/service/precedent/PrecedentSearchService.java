package ga.surilaw.service.precedent;

import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.PrecedentDetail;

public interface PrecedentSearchService {
    PrecedentSearchResponseDto getListSearchResult(PrecedentSearchRequestDto precedentSearchRequestDto);
    PrecedentDetail getDetailSearchResult(int idNum);
}
