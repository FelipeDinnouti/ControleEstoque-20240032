package com.controleestoque.api_estoque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.controleestoque.api_estoque.dto.ClienteResponse;
import com.controleestoque.api_estoque.dto.VendaResponse;
import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.repository.ClienteRepository;
import com.controleestoque.api_estoque.utils.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteResponse createCliente() {
        Cliente cliente = new Cliente();
        clienteRepository.save(cliente);
        return new ClienteResponse(cliente.getId(), new ArrayList<VendaResponse>());
    }

    public ClienteResponse getCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new BadRequestException("Cliente não cadastro"));

        List<VendaResponse> vendaResponseList = new ArrayList<VendaResponse>();

        for (Venda venda : cliente.getVendas()) {
            vendaResponseList.add(VendaService.makeVendaResponse(venda));
        }

        return new ClienteResponse(id, vendaResponseList);
    }

    public void deleteCliente(Long id) {
        clienteRepository.findById(id).orElseThrow(() -> new BadRequestException("Essa venda não existe"));

        clienteRepository.deleteById(id);
    }
}
