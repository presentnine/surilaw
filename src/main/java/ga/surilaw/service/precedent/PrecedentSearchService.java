package ga.surilaw.service.precedent;

import ga.surilaw.domain.entity.Precedent;

import java.util.List;

public interface PrecedentSearchService {
    List<Precedent> getQuickSearchResult(String query);
    List<Precedent> getDetailedSearchResult(String query, String court, String date, String caseNum);
}
