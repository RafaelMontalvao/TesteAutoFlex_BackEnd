package com.Autoflex.TestePratico.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroResponse {

    private List<String> erros;

    public ErroResponse(String mensagem) {
        this(List.of(mensagem));
    }

}
