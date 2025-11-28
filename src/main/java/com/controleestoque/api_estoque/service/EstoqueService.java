package com.controleestoque.api_estoque.service;

import org.springframework.stereotype.Service;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import com.controleestoque.api_estoque.utils.exceptions.OutOfStock;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;

    public void changeStock(Long id, int ammount) {
        Estoque estoque = estoqueRepository.findById(id).orElseThrow(() -> new RuntimeException("Este produto não está cadastrado no estoque"));

        // Not enough in stock
        if ((ammount < 0) && (estoque.getQuantidade() < -ammount)) {
            throw new OutOfStock("Item \"" + id + "\" não tem estoque suficiente.");
        }

        estoque.setQuantidade(estoque.getQuantidade()+ammount);
    }
}
