package com.virtual.application.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateDto {
    private Long id;
    @NotNull
	private Long brandId;
    @NotNull
    private Long productId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private String price;
    @NotNull
    private String currencyCode;
}
