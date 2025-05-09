package com.finance_report.client;

import com.finance_report.company.entity.CompanyEntity;

public interface DartClient {
	CompanyEntity fetchCompany(String corpCode);
}
