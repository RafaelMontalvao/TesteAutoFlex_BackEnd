package com.Autoflex.TestePratico.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMaterialRequestDto {

    private Integer rawMaterialId;
    private BigDecimal quantityNeeded;
}
