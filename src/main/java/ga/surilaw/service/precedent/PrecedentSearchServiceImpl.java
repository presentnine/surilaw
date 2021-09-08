package ga.surilaw.service.precedent;

import ga.surilaw.common.openapi.UriMaker;
import ga.surilaw.common.openapi.XmlParser;
import ga.surilaw.domain.dto.PrecedentSearchRequestDto;
import ga.surilaw.domain.dto.PrecedentSearchResponseDto;
import ga.surilaw.domain.entity.PrecedentDetail;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

// @Value 에러 Mock으로 변경 -엄현식
//@Service
public class PrecedentSearchServiceImpl implements PrecedentSearchService{
    UriMaker uriMaker;
    XmlParser xmlParser;

    public PrecedentSearchServiceImpl(UriMaker uriMaker, XmlParser xmlParser) {
        this.uriMaker = uriMaker;
        this.xmlParser = xmlParser;
    }

    @Override
    public PrecedentSearchResponseDto getListSearchResult(PrecedentSearchRequestDto precedentSearchRequestDto) {
        String uri = uriMaker.makePrecedentListUri(precedentSearchRequestDto);

        PrecedentSearchResponseDto precedentSearchResponseDto = null;

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
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

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
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
