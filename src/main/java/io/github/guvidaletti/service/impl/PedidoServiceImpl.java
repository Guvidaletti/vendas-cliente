package io.github.guvidaletti.service.impl;

import io.github.guvidaletti.domain.entities.Cliente;
import io.github.guvidaletti.domain.entities.ItemPedido;
import io.github.guvidaletti.domain.entities.Pedido;
import io.github.guvidaletti.domain.entities.Produto;
import io.github.guvidaletti.domain.enums.StatusPedido;
import io.github.guvidaletti.domain.repositories.ClienteRepository;
import io.github.guvidaletti.domain.repositories.ItemPedidoRepository;
import io.github.guvidaletti.domain.repositories.PedidoRepository;
import io.github.guvidaletti.domain.repositories.ProdutoRepository;
import io.github.guvidaletti.exception.PedidoNaoEncontradoException;
import io.github.guvidaletti.exception.RegraNegocioException;
import io.github.guvidaletti.rest.dto.ItemPedidoDTO;
import io.github.guvidaletti.rest.dto.PedidoDTO;
import io.github.guvidaletti.service.PedidoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {

  private final ClienteRepository clienteRepository;
  private final ProdutoRepository produtoRepository;
  private final PedidoRepository pedidoRepository;
  private final ItemPedidoRepository itemPedidoRepository;

  @Override
  @Transactional
  public Pedido save(PedidoDTO dto) {
    Cliente cliente = clienteRepository.findById(dto.getCliente()).orElseThrow(() -> new RegraNegocioException("${cliente.nao.encontrado}"));

    Pedido pedido = new Pedido();
    pedido.setTotal(dto.getTotal());
    pedido.setDataPedido(LocalDate.now());
    pedido.setCliente(cliente);
    pedido.setStatus(StatusPedido.REALIZADO);

    List<ItemPedido> itens = converterItens(pedido, dto.getItens());
    pedidoRepository.save(pedido);
    itemPedidoRepository.saveAll(itens);
    pedido.setItens(itens);

    return pedido;
  }

  private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
    if (itens.isEmpty()) {
      throw new RegraNegocioException("${pedido.sem.itens}");
    }

    return itens.stream().map(dto -> {
      Produto produto = produtoRepository.findById(dto.getProduto()).orElseThrow(() -> new RegraNegocioException("Produto inválido!"));

      ItemPedido itemPedido = new ItemPedido();
      itemPedido.setPedido(pedido);
      itemPedido.setQuantidade(dto.getQuantidade());
      itemPedido.setProduto(produto);

      return itemPedido;
    }).toList();
  }

  @Override
  public Optional<Pedido> obterPedidoCompleto(Integer id) {
    return pedidoRepository.findById(id);
  }

  @Override
  @Transactional
  public void atualizarStatus(Integer id, StatusPedido status) {
    pedidoRepository.findById(id).map(p -> {
      p.setStatus(status);
      pedidoRepository.save(p);
      return p;
    }).orElseThrow(PedidoNaoEncontradoException::new);
  }
}
