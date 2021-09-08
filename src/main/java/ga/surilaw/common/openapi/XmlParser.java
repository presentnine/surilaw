package ga.surilaw.common.openapi;

import ga.surilaw.domain.dto.LawSearchResponseDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.PrecedentDetail;
import org.w3c.dom.Document;

public interface XmlParser {
    PrecedentSearchResponseDto parsePrecedentList(Document document);
    LawSearchResponseDto parseLawList(Document document);
    PrecedentDetail parsePrecedentDetail(Document document);
}
