package ga.surilaw.service.precedent;

import ga.surilaw.common.openapi.UriMaker;
import ga.surilaw.common.openapi.XmlParser;
import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.PrecedentDetail;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;

//@Value 에러 Mock으로 변경 -엄현식
@Service
public class MockPrecedentSearchServiceImpl implements PrecedentSearchService{
    UriMaker uriMaker;
    XmlParser xmlParser;

    public MockPrecedentSearchServiceImpl(UriMaker uriMaker, XmlParser xmlParser) {
        this.uriMaker = uriMaker;
        this.xmlParser = xmlParser;
    }

    @Override
    public PrecedentSearchResponseDto getListSearchResult(PrecedentSearchRequestDto precedentSearchRequestDto) {
        String uri = uriMaker.makePrecedentListUri(precedentSearchRequestDto);

        PrecedentSearchResponseDto precedentSearchResponseDto = null;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("referer", "https://open.law.go.kr/");

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(uri), HttpMethod.GET, entity, String.class);

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.getBody())));
            precedentSearchResponseDto = xmlParser.parsePrecedentList(document);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return precedentSearchResponseDto;
    }

    @Override
    public PrecedentDetail getDetailSearchResult(int idNum) {
        String uri = uriMaker.makePrecedentDetailUri(idNum);

        PrecedentDetail precedentDetail = null;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("referer", "https://open.law.go.kr/");

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(uri), HttpMethod.GET, entity, String.class);

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.getBody())));
            precedentDetail = xmlParser.parsePrecedentDetail(document);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return precedentDetail;
    }
}
