package ga.surilaw.service.law;

import ga.surilaw.common.openapi.UriMaker;
import ga.surilaw.common.openapi.XmlParser;
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

//@Service
public class LawSearchServiceImpl implements LawSearchService{
    UriMaker uriMaker;
    XmlParser xmlParser;

    public LawSearchServiceImpl(UriMaker uriMaker, XmlParser xmlParser) {
        this.uriMaker = uriMaker;
        this.xmlParser = xmlParser;
    }

    @Override
    public LawSearchResponseDto getListSearchResult(LawSearchRequestDto lawSearchRequestDto) {
        String uri = uriMaker.makeLawListUri(lawSearchRequestDto);

        LawSearchResponseDto lawSearchResponseDto = null;

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
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
