package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.dto.ClienteResponse;
import com.controleestoque.api_estoque.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/")
    public ResponseEntity<ClienteResponse> createCliente() {
        ClienteResponse response = clienteService.createCliente();
        return ResponseEntity.ok(response); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getCliente(@PathVariable Long id) {
        ClienteResponse response = clienteService.getCliente(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
