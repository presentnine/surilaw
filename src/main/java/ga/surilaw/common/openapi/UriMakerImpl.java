package ga.surilaw.common.openapi;

import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UriMakerImpl implements UriMaker{
    @Value("${openapi.uri.precedentList}")
    String OPENAPI_URI_LIST;
    @Value("${openapi.uri.precedentDetail}")
    String OPENAPI_URI_DETAIL;
    @Value("${openapi.key}")
    String OPENAPI_KEY;

    @Override
    public String makePrecedentListUri(PrecedentSearchRequestDto precedentSearchRequestDto){
        String uri = OPENAPI_URI_LIST;

        // Add OpenAPI key to uri
        uri += "OC=" + OPENAPI_KEY;

        // Add search type: find match from content and title
        uri += "&search=2";

        // Add search target: precedent
        uri += "&target=prec";

        // Add return type: XML
        uri += "&type=XML";

        // Add user search request
        if(precedentSearchRequestDto.getQuery() != null && !precedentSearchRequestDto.getQuery().isBlank()){
            uri = uri + "&query=" + precedentSearchRequestDto.getQuery();
        }
        if(precedentSearchRequestDto.getCourt() != null && !precedentSearchRequestDto.getCourt().isBlank()){
            uri = uri + "&curt=" + precedentSearchRequestDto.getCourt();
        }
        if(precedentSearchRequestDto.getDate() != null && !precedentSearchRequestDto.getDate().isBlank()){
            uri = uri + "&date=" + precedentSearchRequestDto.getDate();
        }
        if(precedentSearchRequestDto.getCaseNum() != null && !precedentSearchRequestDto.getCaseNum().isBlank()){
            uri = uri + "&nb=" + precedentSearchRequestDto.getCaseNum();
        }
        if(precedentSearchRequestDto.getPage() != null && precedentSearchRequestDto.getPage() != 0){
            uri = uri + "&page=" + precedentSearchRequestDto.getPage();
        }

        return uri;
    }

    @Override
    public String makePrecedentDetailUri(int precedentIdNum) {
        String uri = OPENAPI_URI_DETAIL;

        // Add OpenAPI key to uri
        uri += "OC=" + OPENAPI_KEY;

        // Add search type: find match from content and title
        uri += "&search=2";

        // Add search target: precedent
        uri += "&target=prec";

        // Add return type: XML
        uri += "&type=XML";

        // Add precedent ID number
        uri += "&ID=" + precedentIdNum;

        return uri;
    }

    @Override
    public String makeLawListUri(LawSearchRequestDto lawSearchRequestDto) {
        String uri = OPENAPI_URI_LIST;

        // Add OpenAPI key to uri
        uri += "OC=" + OPENAPI_KEY;

        // Add search type: find match from content and title
        uri += "&search=2";

        // Add search target: precedent
        uri += "&target=law";

        // Add return type: XML
        uri += "&type=XML";

        // Add user search request
        if(lawSearchRequestDto.getQuery() != null && !lawSearchRequestDto.getQuery().isBlank()){
            uri = uri + "&query=" + lawSearchRequestDto.getQuery();
        }
        if(lawSearchRequestDto.getDate() != null && !lawSearchRequestDto.getDate().isBlank()){
            uri = uri + "&date=" + lawSearchRequestDto.getDate();
        }
        if(lawSearchRequestDto.getEfYd() != null && !lawSearchRequestDto.getEfYd().isBlank()){
            uri = uri + "&efYd=" + lawSearchRequestDto.getEfYd();
        }
        if(lawSearchRequestDto.getAncYd() != null && lawSearchRequestDto.getAncYd().isBlank()){
            uri = uri + "&ancYd=" + lawSearchRequestDto.getAncYd();
        }

        return uri;
    }
}
