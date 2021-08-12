package ga.surilaw.controller.precedent;

import ga.surilaw.domain.entity.Precedent;
import ga.surilaw.service.precedent.PrecedentSearchService;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/precedent/search")
public class PrecedentSearchController {
    PrecedentSearchService precedentSearchService;

    public PrecedentSearchController(PrecedentSearchService precedentSearchService) {
        this.precedentSearchService = precedentSearchService;
    }

    @GetMapping("/quick")
    public List<Precedent> quickSearch(@RequestParam String query){
        // Todo: Change return value to Dto
        return precedentSearchService.getQuickSearchResult(query);
    }

    @GetMapping("/detailed")
    public List<Precedent> detailedSearch(@RequestParam String query, @Nullable @RequestParam String court, @Nullable @RequestParam String date, @Nullable @RequestParam String caseNum){
        // Todo: Change return value to Dto
        return precedentSearchService.getDetailedSearchResult(query, court, date, caseNum);
    }
}
