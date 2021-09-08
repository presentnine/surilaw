package ga.surilaw.common.openapi;

import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UriMakerTest {
    @Autowired
    UriMaker uriMaker;

    @Test
    public void makePrecedentListUri(){
        PrecedentSearchRequestDto precedentSearchRequestDto = new PrecedentSearchRequestDto(
            "담보",
            2,
            "대법원",
            "20200304",
            "2012가231"
        );
        String expectedUri = "https://www.law.go.kr/DRF/lawSearch.do?OC=test&search=2&target=prec&type=XML&query=담보&curt=대법원&date=20200304&nb=2012가231&page=2";

        String builtUri = uriMaker.makePrecedentListUri(precedentSearchRequestDto);

        Assertions.assertEquals(expectedUri, builtUri);
    }

    @Test
    public void makePrecedentListUriWithNull(){
        PrecedentSearchRequestDto precedentSearchRequestDto = new PrecedentSearchRequestDto(
            "담보",
            null,
            null,
            null,
            null
        );
        String expectedUri = "https://www.law.go.kr/DRF/lawSearch.do?OC=test&search=2&target=prec&type=XML&query=담보";

        String builtUri = uriMaker.makePrecedentListUri(precedentSearchRequestDto);

        Assertions.assertEquals(expectedUri, builtUri);
    }

    @Test
    public void makeLawListUri(){
        LawSearchRequestDto lawSearchRequestDto = new LawSearchRequestDto(
            "담보",
            "20200304",
            "20200426",
            "20200528"
        );
        String expectedUri = "https://www.law.go.kr/DRF/lawSearch.do?OC=test&search=2&target=law&type=XML&query=담보&date=20200304&efYd=20200426&ancYd=20200528";

        String builtUri = uriMaker.makeLawListUri(lawSearchRequestDto);

        Assertions.assertEquals(expectedUri, builtUri);
    }

    @Test
    public void makeLawListUriWithNull(){
        LawSearchRequestDto lawSearchRequestDto = new LawSearchRequestDto(
            "담보",
            null,
            "20200426",
            null
        );
        String expectedUri = "https://www.law.go.kr/DRF/lawSearch.do?OC=test&search=2&target=law&type=XML&query=담보&efYd=20200426";

        String builtUri = uriMaker.makeLawListUri(lawSearchRequestDto);

        Assertions.assertEquals(expectedUri, builtUri);
    }

    @Test
    public void makePrecedentDetailUri(){
        int idNum = 3345;
        String expectedUri = "https://www.law.go.kr/DRF/lawService.do?OC=test&search=2&target=prec&type=XML&ID=3345";

        String builtUri = uriMaker.makePrecedentDetailUri(idNum);

        Assertions.assertEquals(expectedUri, builtUri);
    }
}
