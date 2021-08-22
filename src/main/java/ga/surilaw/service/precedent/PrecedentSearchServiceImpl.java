package ga.surilaw.service.precedent;

import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.PrecedentBrief;
import ga.surilaw.domain.entity.PrecedentDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class PrecedentSearchServiceImpl implements PrecedentSearchService{
    @Value("${openapi.uri.precedentList}")
    String openApi_Uri_PrecedentList;
    @Value("${openapi.uri.precedentDetail}")
    String openApi_Uri_PrecedentDetail;
    @Value("${openapi.key}")
    String openApi_Key;

    @Override
    public PrecedentSearchResponseDto getListSearchResult(PrecedentSearchRequestDto precedentSearchRequestDto) {
        PrecedentSearchResponseDto precedentSearchResponseDto = new PrecedentSearchResponseDto();
        int totalCount = 0;
        ArrayList<PrecedentBrief> precedentBriefList = new ArrayList<>();

        String uri = openApi_Uri_PrecedentList + "OC=" + openApi_Key + "&search=2&target=prec&type=XML&query=" + precedentSearchRequestDto.getQuery();
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
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
            document.getDocumentElement().normalize();

            Node totalCountNode = document.getElementsByTagName("totalCnt").item(0);
            Element totalCountElement = (Element) totalCountNode;
            totalCount = Integer.parseInt(totalCountElement.getTextContent());

            NodeList precList = document.getElementsByTagName("prec");
            for(int count = 0; count < precList.getLength(); count++){
                Node node = precList.item(count);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    PrecedentBrief precedentBrief = new PrecedentBrief(
                            Integer.parseInt(element.getElementsByTagName("판례일련번호").item(0).getTextContent()),
                            element.getElementsByTagName("사건명").item(0).getTextContent(),
                            element.getElementsByTagName("사건번호").item(0).getTextContent(),
                            element.getElementsByTagName("선고일자").item(0).getTextContent(),
                            element.getElementsByTagName("법원명").item(0).getTextContent()
                    );
                    precedentBriefList.add(precedentBrief);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        precedentSearchResponseDto.setTotalCount(totalCount);
        precedentSearchResponseDto.setPrecedentBriefList(precedentBriefList);

        return precedentSearchResponseDto;
    }

    @Override
    public PrecedentDetail getDetailSearchResult(int idNum) {
        PrecedentDetail precedentDetail = null;

        String uri = openApi_Uri_PrecedentDetail + "OC=" + openApi_Key + "&search=2&target=prec&type=XML&ID=" + idNum;

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
            document.getDocumentElement().normalize();

            Node precDetail = document.getElementsByTagName("PrecService").item(0);
            if (precDetail.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) precDetail;
                precedentDetail = new PrecedentDetail(
                        Integer.parseInt(element.getElementsByTagName("판례정보일련번호").item(0).getTextContent()),
                        element.getElementsByTagName("사건명").item(0).getTextContent(),
                        element.getElementsByTagName("사건번호").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("선고일자").item(0).getTextContent()),
                        element.getElementsByTagName("법원명").item(0).getTextContent(),
                        element.getElementsByTagName("사건종류명").item(0).getTextContent(),
                        element.getElementsByTagName("판결유형").item(0).getTextContent(),
                        element.getElementsByTagName("판시사항").item(0).getTextContent(),
                        element.getElementsByTagName("판결요지").item(0).getTextContent(),
                        element.getElementsByTagName("참조조문").item(0).getTextContent(),
                        element.getElementsByTagName("참조판례").item(0).getTextContent(),
                        element.getElementsByTagName("판례내용").item(0).getTextContent());
            }
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
