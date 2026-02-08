package com.Autoflex.TestePratico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponseDto {



        private Integer id;

        private String name;

        private String code;

        private BigDecimal price;

        private List<ProductMaterialResponseDto> materials;





    }


