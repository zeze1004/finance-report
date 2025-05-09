package com.finance_report.client;

import org.springframework.stereotype.Component;

import com.finance_report.company.entity.CompanyEntity;

@Component
public class DartClientImpl implements DartClient {

	@Override
	public CompanyEntity fetchCompany(String corpCode) {
		// TODO: Dart API 호출로 교체 예정
		CompanyEntity company = new CompanyEntity();
		company.setCorpCode(corpCode);
		company.setCorpName("테스트 회사");
		company.setStockCode("000000");
		return company;
	}
}
