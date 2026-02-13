package com.Autoflex.TestePratico.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMaterialResponseDto {


    private Integer id;
    private Integer rawMaterialId;
    private String rawMaterialName;
    private BigDecimal quantityNeeded;

}
