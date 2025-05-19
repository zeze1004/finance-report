package com.finance_report.client;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.finance_report.company.dto.DartCompanyDto;
import com.finance_report.company.entity.CompanyEntity;

@Component
public class DartClientImpl implements DartClient {
	private final String CORP_CODE_PATH = "./CORPCODE.xml";

	@Override
	public CompanyEntity fetchCompany(DartCompanyDto dto) {
		return CompanyEntity.from(dto);
	}

	@Override
	public String fetchCorpCodeByName(String companyName) {
		try {
			File xmlFile = new File(CORP_CODE_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("list");

            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                String name = element.getElementsByTagName("corp_name").item(0).getTextContent();
                if (name.equals(companyName)) {
                    return element.getElementsByTagName("corp_code").item(0).getTextContent();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("기업코드 검색 실패: " + e.getMessage(), e);
        }
        throw new RuntimeException("기업명을 찾을 수 없습니다: " + companyName);
	}
}
