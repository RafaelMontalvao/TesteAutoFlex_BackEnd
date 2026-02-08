package com.Autoflex.TestePratico.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductUpdateRequestDto {

    private String name;


    private String code;


    private BigDecimal price;
}
