package com.finance_report.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenDataClientImpl implements OpenDataClient {

	@Value("${opendata.api.key}")
	private String apiKey;

	private static final String API_URL = "http://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";

	@Override
	public Optional<Long> fetchMarketPrice(String companyName) {
		try {
			String url = UriComponentsBuilder.fromUriString(API_URL)
				.queryParam("serviceKey", apiKey)
				// .queryParam("resultType", "json")
				// .queryParam("basDt", "20250516")
				.queryParam("likeItmsNm", companyName)
				.toUriString();

			log.info("API URL: {}", url);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
			String responseBody = response.getBody();
			
			log.info("API Response: {}", responseBody);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(responseBody);
			
			JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
			log.info("Items node: {}", itemsNode.toPrettyString());
			
			if (itemsNode.isMissingNode() || itemsNode.isEmpty()) {
				log.error("No items found in response for company: {}", companyName);
				return Optional.empty();
			}
			
			// 삼성전자 주식 찾기
			for (JsonNode item : itemsNode) {
				if (companyName.equals(item.path("itmsNm").asText())) {
					String marketPrice = item.path("mrktTotAmt").asText();
					log.info("Market price for {}: {}", companyName, marketPrice);
					return Optional.of(Long.parseLong(marketPrice));
				}
			}
			
			log.error("Company not found in response: {}", companyName);
			return Optional.empty();
		} catch (Exception e) {
			log.error("Error fetching market price for {}: {}", companyName, e.getMessage(), e);
			return Optional.empty();
		}
	}
} 