package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.dto.CreateVendaRequest;
import com.controleestoque.api_estoque.dto.VendaResponse;
import com.controleestoque.api_estoque.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponse> criarVenda(@RequestBody CreateVendaRequest dto) {
        VendaResponse response = vendaService.criarVenda(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> listarVendas() {
        List<VendaResponse> vendas = vendaService.listarVendas();
        return ResponseEntity.ok(vendas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        vendaService.deleteVenda(id);
        return ResponseEntity.noContent().build(); 
    }
}
