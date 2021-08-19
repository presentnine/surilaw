package ga.surilaw.service.law;

import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.LawSearchResponseDto;

public interface LawSearchService {
    LawSearchResponseDto getListSearchResult(LawSearchRequestDto lawSearchRequestDto);
}
