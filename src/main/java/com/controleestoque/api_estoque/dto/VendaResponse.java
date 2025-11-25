package com.controleestoque.api_estoque.dto;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VendaResponse{
    Long id;
    Long clienteId;
    List<ItemVendaResponse> itens;
}