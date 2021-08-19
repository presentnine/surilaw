package ga.surilaw.controller.precedent;

import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.PrecedentDetail;
import ga.surilaw.service.precedent.PrecedentSearchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/precedent")
public class PrecedentSearchController {
    PrecedentSearchService precedentSearchService;

    public PrecedentSearchController(PrecedentSearchService precedentSearchService) {
        this.precedentSearchService = precedentSearchService;
    }


    @GetMapping("/list")
    public PrecedentSearchResponseDto searchList(@RequestBody PrecedentSearchRequestDto precedentSearchRequestDto){
        return precedentSearchService.getListSearchResult(precedentSearchRequestDto);
    }
}
