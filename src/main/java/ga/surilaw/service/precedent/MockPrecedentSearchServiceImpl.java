package ga.surilaw.service.precedent;

import ga.surilaw.domain.entity.Precedent;
import org.springframework.http.*;
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
import java.util.List;

public class MockPrecedentSearchServiceImpl implements PrecedentSearchService{
    //@Value("${openapi.uri.precedentList}")
    String openApi_Uri = "https://www.law.go.kr/DRF/lawSearch.do?";
    //@Value("${openapi.key}")
    String openApi_Key = "test";

    @Override
    public List<Precedent> getQuickSearchResult(String query) {
        // Todo: Change return value to Dto
        ArrayList<Precedent> precedentList = new ArrayList<>();

        String uri = openApi_Uri + "OC=" + openApi_Key + "&search=2&target=prec&type=XML&query=" + query;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("referer", "https://open.law.go.kr/");

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(uri), HttpMethod.GET, entity, String.class);

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.getBody())));
            document.getDocumentElement().normalize();

            NodeList precList = document.getElementsByTagName("prec");
            for(int count = 0; count < precList.getLength(); count++){
                Node node = precList.item(count);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    Precedent precedent = new Precedent(
                            Integer.parseInt(element.getElementsByTagName("판례일련번호").item(0).getTextContent()),
                            element.getElementsByTagName("사건명").item(0).getTextContent(),
                            element.getElementsByTagName("사건번호").item(0).getTextContent(),
                            element.getElementsByTagName("선고일자").item(0).getTextContent(),
                            element.getElementsByTagName("법원명").item(0).getTextContent()
                    );
                    precedentList.add(precedent);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return precedentList;
    }

    @Override
    public List<Precedent> getDetailedSearchResult(String query, String court, String date, String caseNum) {
        // Todo: Change return value to Dto
        ArrayList<Precedent> precedentList = new ArrayList<>();

        String uri = openApi_Uri + "OC=" + openApi_Key + "&search=2&target=prec&type=XML&query=" + query;
        if(court != null && !court.isBlank()){
            uri = uri + "&curt=" + court;
        }
        if(court != null && !date.isBlank()){
            uri = uri + "&date=" + date;
        }
        if(court != null && !caseNum.isBlank()){
            uri = uri + "&nb=" + caseNum;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("referer", "https://open.law.go.kr/");

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(uri), HttpMethod.GET, entity, String.class);

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(response.getBody())));
            document.getDocumentElement().normalize();

            NodeList precList = document.getElementsByTagName("prec");
            for(int count = 0; count < precList.getLength(); count++){
                Node node = precList.item(count);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    Precedent precedent = new Precedent(
                            Integer.parseInt(element.getElementsByTagName("판례일련번호").item(0).getTextContent()),
                            element.getElementsByTagName("사건명").item(0).getTextContent(),
                            element.getElementsByTagName("사건번호").item(0).getTextContent(),
                            element.getElementsByTagName("선고일자").item(0).getTextContent(),
                            element.getElementsByTagName("법원명").item(0).getTextContent()
                    );
                    precedentList.add(precedent);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return precedentList;
    }
}
