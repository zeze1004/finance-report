package com.finance_report.company.service;

import org.springframework.stereotype.Service;

import com.finance_report.client.DartClient;
import com.finance_report.company.dto.DartCompanyDto;
import com.finance_report.company.entity.CompanyEntity;
import com.finance_report.company.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	
	private final DartClient dartClient;
	private final CompanyRepository companyRepository;
	
	public DartCompanyDto getCompany(String corpCode) {
		CompanyEntity entity = companyRepository.findById(corpCode)
				.orElseThrow(() -> new RuntimeException("회사 정보를 찾을 수 없습니다."));
		return DartCompanyDto.from(entity);
	}

	public DartCompanyDto addCompany(DartCompanyDto dto) {
		CompanyEntity entity = CompanyEntity.from(dto);
		CompanyEntity savedEntity = companyRepository.save(entity);
		return DartCompanyDto.from(savedEntity);
	}

	public DartCompanyDto getOrFetchCompany(String corpCode) {
		return companyRepository.findById(corpCode)
				.map(DartCompanyDto::from)
				.orElseGet(() -> {
					DartCompanyDto dto = new DartCompanyDto();
					dto.setCorpCode(corpCode);
					CompanyEntity fromDartCompany = dartClient.fetchCompany(dto);
					CompanyEntity savedEntity = companyRepository.save(fromDartCompany);
					return DartCompanyDto.from(savedEntity);
				});
	}
}
