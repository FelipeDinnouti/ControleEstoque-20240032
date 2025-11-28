package com.controleestoque.api_estoque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.controleestoque.api_estoque.dto.CreateVendaRequest;
import com.controleestoque.api_estoque.dto.ItemVendaRequest;
import com.controleestoque.api_estoque.dto.ItemVendaResponse;
import com.controleestoque.api_estoque.dto.VendaResponse;
import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.model.ItemVenda;
import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.repository.ClienteRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.repository.VendaRepository;
import com.controleestoque.api_estoque.utils.exceptions.BadRequestException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueService estoqueService;

    public static VendaResponse makeVendaResponse(Venda venda) {
        List<ItemVenda> itemVendaList = venda.getItens();
        List<ItemVendaResponse> serialized = new ArrayList<ItemVendaResponse>();

        // Transform ItemVenda into ItemVendaResponse (DTO form again)
        for (ItemVenda itemVenda : itemVendaList ) {
            serialized.add(new ItemVendaResponse(itemVenda.getId(), itemVenda.getProduto().getId(), itemVenda.getQuantidade(), itemVenda.getPrecoUnitario()));
        }

        return new VendaResponse(venda.getId(), venda.getCliente().getId(), serialized);
    }

    
    @Transactional // This annotation basically implements roll back
    public VendaResponse criarVenda(CreateVendaRequest dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new BadRequestException("Cliente não encontrado"));

        Venda venda = new Venda(cliente);

        for (ItemVendaRequest itemDto : dto.getItens()) {
        
            // Make sure the products specified exists
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new BadRequestException("Produto não encontrado"));
            
            // Make each sale item
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setVenda(venda);
            item.setQuantidade(itemDto.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            // Update stock of item
            estoqueService.changeStock(item.getProduto().getId(), -item.getQuantidade()); // Throws an exception if there isn't enough stock
           
            venda.getItens().add(item); // owning side is updated by the setters
        }

        Venda saved = vendaRepository.save(venda);
        return makeVendaResponse(saved);
    }

    public List<VendaResponse> listarVendas() {

        List<VendaResponse> res = new ArrayList<VendaResponse>();
        List<Venda> data = vendaRepository.findAll();
        
        // Transform Venda into VendaResponse (it's DTO form)
        for (Venda venda : data) {
            res.add(makeVendaResponse(venda));
        }
 
        return res;
    }

    public boolean deleteVenda(Long id) {
        vendaRepository.findById(id).orElseThrow(() -> new BadRequestException("Essa venda não existe"));

        vendaRepository.deleteById(id);

        return true;
    }
}
