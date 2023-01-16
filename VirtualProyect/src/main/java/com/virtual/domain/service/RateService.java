package com.virtual.domain.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtual.domain.model.CurrenciesListResponse;
import com.virtual.domain.model.CurrencyResponse;
import com.virtual.domain.model.RateFormattedModel;
import com.virtual.domain.model.RateModel;
import com.virtual.infrastucture.entity.RateEntity;
import com.virtual.infrastucture.repository.RateRepository;

@Service
public class RateService {
	
	private static final String RATE_NOT_FOUND = "Rate not found";
	private static final String ERROR_SAVING_RATE = "Error saving rate";
	@Autowired
	RateRepository rateRepository;
	@Autowired
	CurrencyService currencyService;
	
	public RateModel createRate(RateModel rateModel) throws Exception{
		try {
			RateEntity rateEntity = new RateEntity();
			BeanUtils.copyProperties(rateEntity, rateModel);
			
			CurrenciesListResponse currencies = currencyService.getCurrencies();
			if(currencies.getCurrencies().stream().noneMatch(cur->cur.equals(rateModel.getCurrencyCode()))) {
				throw new Exception("Currency not found");
			}
			
			rateRepository.save(rateEntity);
			BeanUtils.copyProperties(rateModel, rateEntity);
			return rateModel;
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new Exception(ERROR_SAVING_RATE);
		}
	}
	
	public RateFormattedModel getRateById(Long id) throws Exception{
		try {
			RateEntity rateEntity = rateRepository.findById(id).orElseThrow(()->new Exception(RATE_NOT_FOUND));
			return buildFormattedResponse(rateEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw e;
		}
	}
	
	public RateModel updateRate(Long idRate, Long price) throws Exception{
		try {
			RateEntity rateEntity = rateRepository.findById(idRate).orElseThrow(()->new Exception(RATE_NOT_FOUND));
			rateEntity.setPrice(price);
			rateRepository.save(rateEntity);
			RateModel rateModel = new RateModel();
			BeanUtils.copyProperties(rateModel, rateEntity);
			return rateModel;
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new Exception("Error updating rate");
		}
	}
	
	public RateModel deleteRate(Long idRate) throws Exception{
		try {
			RateEntity rateEntity = rateRepository.findById(idRate).orElseThrow(()->new Exception(RATE_NOT_FOUND));
			rateRepository.delete(rateEntity);
			RateModel rateModel = new RateModel();
			BeanUtils.copyProperties(rateModel, rateEntity);
			return rateModel;
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new Exception("Error deleting rate");
		}
	}
	
	
	public RateFormattedModel getRateByDateProductBrand(LocalDate date, Long productId, Long brandId) throws Exception{
		try {
			RateEntity rateEntity = 
					rateRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandId(date, date, productId, brandId)
						.orElseThrow(()->new Exception(RATE_NOT_FOUND));
			return buildFormattedResponse(rateEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw e;
		}
	}

	private RateFormattedModel buildFormattedResponse(RateEntity rateEntity)
			throws Exception, IllegalAccessException, InvocationTargetException {
		CurrencyResponse response = currencyService.getCurrencyByCode(rateEntity.getCurrencyCode());
		RateFormattedModel rateModel = new RateFormattedModel();
		BeanUtils.copyProperties(rateModel, rateEntity);
		rateModel.setPrice(BigDecimal.valueOf(rateEntity.getPrice(), 0).setScale(response.getDecimals()).toString()+response.getSymbol());
		rateModel.setCurrencyCode(response.getCode());
		return rateModel;
	}
	
}
