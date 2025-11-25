package com.controleestoque.api_estoque.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemVendaResponse {
    Long id;
    Long produtoId;
    Integer quantidade;
    BigDecimal precoUnitario;
}