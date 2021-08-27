package ga.surilaw.controller.law;

import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.LawSearchResponseDto;
import ga.surilaw.service.law.LawSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LawSearchController {
    LawSearchService lawSearchService;

    public LawSearchController(LawSearchService lawSearchService) {
        this.lawSearchService = lawSearchService;
    }

    @GetMapping("/api/law/list")
    public ResponseEntity<LawSearchResponseDto> searchList(@RequestBody LawSearchRequestDto lawSearchRequestDto){
        return ResponseEntity.ok(lawSearchService.getListSearchResult(lawSearchRequestDto));
    }
}
