package ga.surilaw.service.law;

import ga.surilaw.common.openapi.UriMaker;
import ga.surilaw.common.openapi.XmlParser;
import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.LawSearchResponseDto;
import ga.surilaw.domain.entity.LawBrief;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;

@Service
public class MockLawSearchServiceImpl implements LawSearchService{
    UriMaker uriMaker;
    XmlParser xmlParser;

    public MockLawSearchServiceImpl(UriMaker uriMaker, XmlParser xmlParser) {
        this.uriMaker = uriMaker;
        this.xmlParser = xmlParser;
    }

    @Override
    public LawSearchResponseDto getListSearchResult(LawSearchRequestDto lawSearchRequestDto) {
        String uri = uriMaker.makeLawListUri(lawSearchRequestDto);

        LawSearchResponseDto lawSearchResponseDto = null;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("referer", "https://open.law.go.kr/");

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(uri), HttpMethod.GET, entity, String.class);

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.getBody())));
            lawSearchResponseDto = xmlParser.parseLawList(document);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return lawSearchResponseDto;
    }
}
