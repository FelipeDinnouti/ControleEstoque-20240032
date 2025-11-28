package com.controleestoque.api_estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemVendaRequest {
    Long produtoId;
    Integer quantidade;
}
