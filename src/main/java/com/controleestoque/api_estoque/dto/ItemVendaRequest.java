package com.controleestoque.api_estoque.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemVendaRequest {
    Long produtoId;
    Integer quantidade;
}
