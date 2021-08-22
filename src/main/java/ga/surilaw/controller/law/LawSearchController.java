package ga.surilaw.controller.law;

import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.LawSearchResponseDto;
import ga.surilaw.service.law.LawSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/law")
public class LawSearchController {
    LawSearchService lawSearchService;

    public LawSearchController(LawSearchService lawSearchService) {
        this.lawSearchService = lawSearchService;
    }

    @GetMapping("/list")
    public LawSearchResponseDto searchList(@RequestBody LawSearchRequestDto lawSearchRequestDto){
        // Todo: Change return value to Dto
        return lawSearchService.getListSearchResult(lawSearchRequestDto);
    }
}
