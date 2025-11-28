package com.controleestoque.api_estoque.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProdutoResponse {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String categoria;
    private Integer estoque;
}
