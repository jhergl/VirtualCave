package com.virtual.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtual.domain.model.CurrencyResponse;

class CurrencyServiceTest {
	
	@Autowired
	CurrencyService currencyService =  Mockito.mock(CurrencyService.class);
	
	@BeforeEach
	void setUp() {
		try {
			Mockito.when(currencyService.getCurrencyByCode(anyString())).thenReturn(new CurrencyResponse("€","EUR", 2));
		} catch (Exception e) {
		}
	}

	@Test
	void getCurrencyByCode() {
		
		CurrencyResponse currency;
		try {
			currency = currencyService.getCurrencyByCode("EUR");
			
			assertThat(currency, hasProperty("symbol", equalTo("€")));
		    assertThat(currency, hasProperty("code", equalTo("EUR")));
		    assertThat(currency, hasProperty("decimals", equalTo(2)));
		} catch (Exception e) {
		}
	}

}
