package com.finance_report.client;

import java.util.Optional;

public interface OpenDataClient {
	Optional<Long> fetchMarketPrice(String companyName);
}
