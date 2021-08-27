package ga.surilaw.controller.precedent;

import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.PrecedentDetail;
import ga.surilaw.service.precedent.PrecedentSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PrecedentSearchController {
    PrecedentSearchService precedentSearchService;

    public PrecedentSearchController(PrecedentSearchService precedentSearchService) {
        this.precedentSearchService = precedentSearchService;
    }


    @GetMapping("/api/precedent/list")
    public ResponseEntity<PrecedentSearchResponseDto> searchList(@RequestBody PrecedentSearchRequestDto precedentSearchRequestDto){
        return ResponseEntity.ok(precedentSearchService.getListSearchResult(precedentSearchRequestDto));
    }

    @GetMapping("/api/precedent/detail")
    public ResponseEntity<PrecedentDetail> searchDetail(@RequestParam int idNum){
        return ResponseEntity.ok(precedentSearchService.getDetailSearchResult(idNum));
    }
}
