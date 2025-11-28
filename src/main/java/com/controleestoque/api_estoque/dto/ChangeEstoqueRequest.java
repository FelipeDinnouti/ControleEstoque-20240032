package com.controleestoque.api_estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChangeEstoqueRequest {
    private Integer quantidade;

    public Integer getQuantidade() {
        return quantidade;
    }
}
