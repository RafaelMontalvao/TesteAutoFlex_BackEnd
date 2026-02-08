package com.Autoflex.TestePratico.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAvailabilityDto {

    private Integer productId;
    private String productName;
    private BigDecimal producibleQuantity;
    private String productcode;
    private BigDecimal totalProductionValue;
}
