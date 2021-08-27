package ga.surilaw.service.law;

import ga.surilaw.common.openapi.UriMaker;
import ga.surilaw.domain.dto.LawSearchRequestDto;
import ga.surilaw.domain.dto.LawSearchResponseDto;
import ga.surilaw.domain.entity.LawBrief;
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

@Service
public class LawSearchServiceImpl implements LawSearchService{
    UriMaker uriMaker;

    public LawSearchServiceImpl(UriMaker uriMaker) {
        this.uriMaker = uriMaker;
    }

    @Override
    public LawSearchResponseDto getListSearchResult(LawSearchRequestDto lawSearchRequestDto) {
        String uri = uriMaker.makeLawListUri(lawSearchRequestDto);

        LawSearchResponseDto lawSearchResponseDto = new LawSearchResponseDto();
        int totalCount = 0;
        ArrayList<LawBrief> lawBriefList = new ArrayList<>();

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
            document.getDocumentElement().normalize();

            Node totalCountNode = document.getElementsByTagName("totalCnt").item(0);
            Element totalCountElement = (Element) totalCountNode;
            totalCount = Integer.parseInt(totalCountElement.getTextContent());

            NodeList lawList = document.getElementsByTagName("law");
            for(int count = 0; count < lawList.getLength(); count++){
                Node node = lawList.item(count);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    LawBrief lawBrief = new LawBrief(
                            Integer.parseInt(element.getElementsByTagName("법령일련번호").item(0).getTextContent()),
                            element.getElementsByTagName("현행연혁코드").item(0).getTextContent(),
                            element.getElementsByTagName("법령명한글").item(0).getTextContent(),
                            element.getElementsByTagName("법령약칭명").item(0).getTextContent(),
                            Integer.parseInt(element.getElementsByTagName("공포일자").item(0).getTextContent()),
                            element.getElementsByTagName("법령구분명").item(0).getTextContent(),
                            Integer.parseInt(element.getElementsByTagName("시행일자").item(0).getTextContent())
                    );
                    lawBriefList.add(lawBrief);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        lawSearchResponseDto.setTotalCount(totalCount);
        lawSearchResponseDto.setPrecedentBriefList(lawBriefList);

        return lawSearchResponseDto;
    }
}
