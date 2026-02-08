package com.Autoflex.TestePratico.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class RawMaterialUpdateRequestDto {

    private String name;


    private BigDecimal stockQuantity;
}

