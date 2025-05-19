package com.finance_report.company.entity;

import com.finance_report.company.dto.DartCompanyDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
public class CompanyEntity {

    @Id
    private String corpCode; // 고유 기업 코드 (DART 기준)

    private String corpName; // 기업명

    private String stockCode; // 종목 코드

    public static CompanyEntity from(DartCompanyDto dto) {
        CompanyEntity entity = new CompanyEntity();
        entity.setCorpCode(dto.getCorpCode());
        entity.setCorpName(dto.getCorpName());
        entity.setStockCode(dto.getStockCode());
        return entity;
    }
}