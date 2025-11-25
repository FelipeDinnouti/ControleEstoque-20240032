package com.controleestoque.api_estoque.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "tb_clientes")
public class Cliente {  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JoinColumn(name = "vendas_id", nullable = false)
    private List<Venda> vendas = new ArrayList<Venda>();

    public Cliente() {}

    public Cliente(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public void addVenda(Venda venda) { 
        this.vendas.add(venda);
        venda.setCliente(this); 
    }

    public void removeVenda(Venda venda) {
        vendas.remove(venda);
        venda.setCliente(null);
    }
}
