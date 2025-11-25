package com.controleestoque.api_estoque.dto;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateVendaRequest {
    Long clienteId;
    List<ItemVendaRequest> itens;
}