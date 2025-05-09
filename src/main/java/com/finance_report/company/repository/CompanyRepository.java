package com.finance_report.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finance_report.company.entity.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
	
}
