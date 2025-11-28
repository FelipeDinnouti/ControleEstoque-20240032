package com.controleestoque.api_estoque.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.controleestoque.api_estoque.dto.CreateProdutoRequest;
import com.controleestoque.api_estoque.dto.ProdutoResponse;
import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.utils.exceptions.BadRequestException;
import com.controleestoque.api_estoque.model.Categoria;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final EstoqueRepository estoqueRepository;

    public ProdutoResponse produtoToResponse(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getPreco(), produto.getCategoria().getNome(), produto.getEstoque().getQuantidade());
    }

    @GetMapping("/")
    public ResponseEntity<List<ProdutoResponse>> getAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoResponse> produtosResponse = new ArrayList<ProdutoResponse>();
        for (Produto p : produtos) {
            produtosResponse.add(produtoToResponse(p));
        }
        return ResponseEntity.ok().body(produtosResponse);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> getProdutoById(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new BadRequestException("Produto não existe"));
        return ResponseEntity.ok().body(produtoToResponse(produto));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProdutoResponse> createProduto(@RequestBody CreateProdutoRequest t_produto) {
        if (t_produto.getCategoria() == null) {
            throw new BadRequestException("Categoria do produto inválida");
        }

        Categoria categoria = categoriaRepository.findById(t_produto.getCategoria()).orElseThrow(() -> new BadRequestException("Essa categoria não existe"));

        Produto produto = new Produto();
        produto.setNome(t_produto.getNome());
        produto.setPreco(t_produto.getPreco());
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        Estoque estoque = new Estoque();
        estoque.setQuantidade(50);
        estoque.setProduto(produto);

        estoqueRepository.save(estoque);

        produto.setEstoque(estoque);
        produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoToResponse(produtoRepository.save(produto)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new BadRequestException("Produto não existe"));
        produto.setNome(produtoDetails.getNome());
        Produto updatedProduto = produtoRepository.save(produto);

        return ResponseEntity.ok(updatedProduto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
