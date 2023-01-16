package com.virtual.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.virtual.domain.model.CurrenciesListResponse;
import com.virtual.domain.model.CurrencyResponse;

@Service
public class CurrencyService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${thirdParty.baseUrl}")
	private String url;
	
	
    
    public CurrenciesListResponse getCurrencies() {
    	RestTemplate restTemplate = new RestTemplate();
    	try {
    		return restTemplate.exchange(
    				url+"/v1/currencies", 
    				HttpMethod.GET,
    				null,
    				CurrenciesListResponse.class).getBody();
    	}catch (Exception e) {
			throw e;
		}
    }
    
    public CurrencyResponse getCurrencyByCode(String currencyCode) throws Exception {
    	RestTemplate restTemplate = new RestTemplate();
    	try {
    		return restTemplate.exchange(
    				url+"/v1/currencies/"+ currencyCode, 
    				HttpMethod.GET,
    				null,
    				CurrencyResponse.class).getBody();
    	}catch (Exception e) {
			throw new Exception("Currency not found");
		}
    }


	
}
