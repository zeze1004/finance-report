package com.finance_report.company.service;

import org.springframework.stereotype.Service;

import com.finance_report.client.DartClient;
import com.finance_report.company.entity.CompanyEntity;
import com.finance_report.company.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	
	private final DartClient dartClient;
	private final CompanyRepository companyRepository;
	
	public CompanyEntity getCompany(String corpCode) {
		return companyRepository.findById(corpCode)
				.orElseThrow(() -> new RuntimeException("회사 정보를 찾을 수 없습니다."));
	}

	public CompanyEntity addCompany(CompanyEntity company) {
		return companyRepository.save(company);
	}

	public CompanyEntity getOrFetchCompany(String corpCode) {
		return companyRepository.findById(corpCode)
				.orElseGet(() -> {
					CompanyEntity fromDartCompany = dartClient.fetchCompany(corpCode);
					return companyRepository.save(fromDartCompany);
				});
	}
}
