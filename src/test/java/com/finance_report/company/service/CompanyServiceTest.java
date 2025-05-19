package com.finance_report.company.service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import com.finance_report.client.DartClient;
import com.finance_report.company.dto.DartCompanyDto;
import com.finance_report.company.entity.CompanyEntity;
import com.finance_report.company.repository.CompanyRepository;

public class CompanyServiceTest {

	private CompanyService companyService;
	private CompanyRepository companyRepository;
	private DartClient dartClient;
	
	@BeforeEach
	public void setUp() {
		companyRepository = Mockito.mock(CompanyRepository.class);
		dartClient = Mockito.mock(DartClient.class);
		companyService = new CompanyService(dartClient, companyRepository);
	}

	@Test
	@DisplayName("DB에 회사 정보가 있으면 DB에서 조회한 정보를 반환")
	public void getOrFetchCompany_WhenCompanyExistsInDB_ReturnsCompanyFromDB() {
		// given
		String corpCode = "00126380";
		CompanyEntity companyEntity = new CompanyEntity();
		companyEntity.setCorpCode(corpCode);
		companyEntity.setCorpName("삼성전자");
		companyEntity.setStockCode("005930");

		when(companyRepository.findById(corpCode))
			.thenReturn(Optional.of(companyEntity));

		// when
		DartCompanyDto result = companyService.getOrFetchCompany(corpCode);

		// then
		assertThat(result.getCorpCode()).isEqualTo(corpCode);
		assertThat(result.getCorpName()).isEqualTo("삼성전자");
		assertThat(result.getStockCode()).isEqualTo("005930");
	}

	@Test
	@DisplayName("DB에 회사 정보가 없으면 Dart API 호출 후 저장된 정보를 반환")
	public void getOrFetchCompany_WhenCompanyNotExistsInDB_FetchesFromDartAndReturns() {
		// given
		String corpCode = "00126380";
		CompanyEntity companyEntity = new CompanyEntity();
		companyEntity.setCorpCode(corpCode);
		companyEntity.setCorpName("삼성전자");
		companyEntity.setStockCode("005930");

		when(companyRepository.findById(corpCode))
			.thenReturn(Optional.empty());
		when(dartClient.fetchCompany(any(DartCompanyDto.class)))
			.thenReturn(companyEntity);
		when(companyRepository.save(any(CompanyEntity.class)))
			.thenReturn(companyEntity);

		// when
		DartCompanyDto result = companyService.getOrFetchCompany(corpCode);

		// then
		assertThat(result.getCorpCode()).isEqualTo(corpCode);
		assertThat(result.getCorpName()).isEqualTo("삼성전자");
		assertThat(result.getStockCode()).isEqualTo("005930");
	}
}
