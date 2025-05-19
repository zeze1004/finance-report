package com.finance_report.company.dto;

import com.finance_report.company.entity.CompanyEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DartCompanyDto {
	private String corpCode;
	private String corpName;
	private String stockCode;

	public static DartCompanyDto from(CompanyEntity entity) {
		DartCompanyDto dto = new DartCompanyDto();
		dto.corpCode = entity.getCorpCode();
		dto.corpName = entity.getCorpName();
		dto.stockCode = entity.getStockCode();
		return dto;
	}
}
