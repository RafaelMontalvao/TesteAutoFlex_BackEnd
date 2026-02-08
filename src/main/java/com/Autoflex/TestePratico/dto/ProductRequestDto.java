package com.Autoflex.TestePratico.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotEmpty(message = "Campo obrigatório")
    private String name;

    @NotEmpty(message = "Campo obrigatório")
    private String code;

    @NotNull(message = "Campo Obrigatório")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

}
