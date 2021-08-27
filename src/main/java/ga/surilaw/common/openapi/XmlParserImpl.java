package ga.surilaw.common.openapi;

import ga.surilaw.domain.dto.LawSearchResponseDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.LawBrief;
import ga.surilaw.domain.entity.PrecedentBrief;
import ga.surilaw.domain.entity.PrecedentDetail;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class XmlParserImpl implements XmlParser{

    @Override
    public PrecedentSearchResponseDto parsePrecedentList(Document document) {
        PrecedentSearchResponseDto precedentSearchResponseDto = new PrecedentSearchResponseDto();

        int totalCount = 0;
        ArrayList<PrecedentBrief> precedentBriefList = new ArrayList<>();

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

        precedentSearchResponseDto.setTotalCount(totalCount);
        precedentSearchResponseDto.setPrecedentBriefList(precedentBriefList);

        return precedentSearchResponseDto;
    }

    @Override
    public LawSearchResponseDto parseLawList(Document document) {
        LawSearchResponseDto lawSearchResponseDto = new LawSearchResponseDto();

        int totalCount = 0;
        ArrayList<LawBrief> lawBriefList = new ArrayList<>();

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

        lawSearchResponseDto.setTotalCount(totalCount);
        lawSearchResponseDto.setPrecedentBriefList(lawBriefList);

        return  lawSearchResponseDto;
    }

    @Override
    public PrecedentDetail parsePrecedentDetail(Document document) {
        PrecedentDetail precedentDetail = null;

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

        return precedentDetail;
    }
}
