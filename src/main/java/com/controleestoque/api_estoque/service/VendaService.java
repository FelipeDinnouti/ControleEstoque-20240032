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

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaResponse criarVenda(CreateVendaRequest dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Venda venda = new Venda(cliente);

        for (ItemVendaRequest itemDto : dto.itens()) {

            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setVenda(venda);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            venda.getItens().add(item); // owning side is updated by the setters
        }

        Venda saved = vendaRepository.save(venda);

        return toResponse(saved);
    }

    public List<VendaResponse> listarVendas() {

        List<VendaResponse> res = new ArrayList<VendaResponse>();
        List<Venda> data = vendaRepository.findAll();
        
        // Transform Venda into VendaResponse (it's DTO form)
        for (Venda venda : data) {
            List<ItemVenda> itemVendaList = venda.getItens();
            List<ItemVendaResponse> serialized = new ArrayList<ItemVendaResponse>();

            // Transform ItemVenda into ItemVendaResponse (DTO form again)
            for (ItemVenda itemVenda : itemVendaList ) {
                serialized.add(new ItemVendaResponse(itemVenda.getId(), itemVenda.getProduto().getId(), itemVenda.getQuantidade(), itemVenda.getPrecoUnitario()));
            }

            VendaResponse resObject = new VendaResponse(venda.getId(), venda.getCliente().getId(), serialized);
            res.add(resObject);
        }
 
        return res;
    }
}
