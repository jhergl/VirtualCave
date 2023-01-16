package com.virtual.application.rest;

import java.time.LocalDate;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtual.application.config.ApiError;
import com.virtual.application.model.RateDto;
import com.virtual.application.model.RateFormattedDto;
import com.virtual.application.model.RateUpdateDto;
import com.virtual.domain.model.RateFormattedModel;
import com.virtual.domain.model.RateModel;
import com.virtual.domain.service.RateService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("tarifa")
public class RateController {
	
	@Autowired
	RateService rateService;
	
	@PostMapping("")
	public RateDto createRate(@Valid @RequestBody RateDto rateDto) throws Exception {
		RateModel rateModel = new RateModel();
		try {
			BeanUtils.copyProperties(rateModel, rateDto);
			rateModel = rateService.createRate(rateModel);
			BeanUtils.copyProperties(rateDto, rateModel);
			return rateDto;
		} catch (Exception e) {
			throw new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public RateFormattedDto getRate(@PathVariable("id") final Long id) throws Exception {
		RateFormattedDto rateDto = new RateFormattedDto();
		try {
			RateFormattedModel rateModel = rateService.getRateById(id);
			BeanUtils.copyProperties(rateDto, rateModel);
			return rateDto;
		} catch (Exception e) {
			throw new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public RateDto updateRate(@Valid @RequestBody RateUpdateDto requestRate, @PathVariable("id") final Long id) throws Exception {
		RateDto rateDto = new RateDto();
		try {
			RateModel rateModel = rateService.updateRate(id, requestRate.getPrice());
			BeanUtils.copyProperties(rateDto, rateModel);
			return rateDto;
		} catch (Exception e) {
			throw new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public RateDto deleteRate(@PathVariable("id") final Long id) throws Exception {
		RateDto rateDto = new RateDto();
		try {
			RateModel rateModel = rateService.deleteRate(id);
			BeanUtils.copyProperties(rateDto, rateModel);
			return rateDto;
		} catch (Exception e) {
			throw new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/{date}/{productId}/{brandId}")
	public RateFormattedDto getRateByDateProductBrand(@PathVariable("date") final LocalDate date, 
			@PathVariable("productId") final Long productId,
			@PathVariable("brandId") final Long brandId) throws Exception {
		RateFormattedDto rateDto = new RateFormattedDto();
		try {
			RateFormattedModel rateModel = rateService.getRateByDateProductBrand(date, productId, brandId);
			BeanUtils.copyProperties(rateDto, rateModel);
			return rateDto;
		} catch (Exception e) {
			throw new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
