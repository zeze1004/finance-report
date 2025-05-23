package com.finance_report.company.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance_report.client.OpenDataClient;
import com.finance_report.company.dto.DartCompanyDto;
import com.finance_report.company.service.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final OpenDataClient openDataClient;

    @GetMapping("corp-code/{corpCode}")
    public DartCompanyDto getCompany(@PathVariable String corpCode) {
        return companyService.getOrFetchCompany(corpCode);
    }

    @PostMapping
    public DartCompanyDto addCompany(@RequestBody DartCompanyDto dto) {
        return companyService.addCompany(dto);
    }

    @GetMapping("name/{companyName}")
    public String getCorpCode(@PathVariable String companyName) {
        return companyService.getCorpCode(companyName);
    }

    @GetMapping("market-price/{companyName}")
    public Long getMarketPrice(@PathVariable String companyName) {
        return openDataClient.fetchMarketPrice(companyName)
            .orElseThrow(() -> new RuntimeException("시세 정보를 찾을 수 없습니다: " + companyName));
    }
}