package com.controleestoque.api_estoque.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VendaResponse{
    Long id;
    Long clienteId;
    List<ItemVendaResponse> itens;
}