package com.finance_report.client;

import org.springframework.stereotype.Component;

import com.finance_report.company.dto.DartCompanyDto;
import com.finance_report.company.entity.CompanyEntity;

@Component
public class DartClientImpl implements DartClient {

	@Override
	public CompanyEntity fetchCompany(DartCompanyDto dto) {
		return CompanyEntity.from(dto);
	}
}
