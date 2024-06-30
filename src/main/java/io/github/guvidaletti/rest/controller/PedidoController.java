package io.github.guvidaletti.rest.controller;

import io.github.guvidaletti.domain.entities.ItemPedido;
import io.github.guvidaletti.domain.entities.Pedido;
import io.github.guvidaletti.domain.enums.StatusPedido;
import io.github.guvidaletti.rest.dto.AtualizarStatusPedidoDTO;
import io.github.guvidaletti.rest.dto.InformacoesItensPedidoDTO;
import io.github.guvidaletti.rest.dto.InformacoesPedidoDTO;
import io.github.guvidaletti.rest.dto.PedidoDTO;
import io.github.guvidaletti.service.PedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {

  private PedidoService pedidoService;

  @GetMapping({"", "/"})
  public String getPedidos() {
    String teste = "";
    return "Pedidos 3";
  }

  @PostMapping({"", "/"})
  @ResponseStatus(HttpStatus.CREATED)
  public Integer save(@RequestBody @Valid PedidoDTO dto) {
    Pedido pedido = pedidoService.save(dto);
    return pedido.getId();
  }

  @GetMapping({"/{id}", "/{id}/"})
  public InformacoesPedidoDTO getById(@PathVariable Integer id) {
    return pedidoService.obterPedidoCompleto(id).map(this::converterPedido
    ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "{pedido.nao.encontrado}"));
  }

  private InformacoesPedidoDTO converterPedido(Pedido pedido) {
    return InformacoesPedidoDTO
        .builder()
        .codigo(pedido.getId())
        .total(pedido.getTotal())
        .cpf(pedido.getCliente().getCpf())
        .nomeCliente(pedido.getCliente().getNome())
        .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("DD/MM/YYYY")))
        .itens(converterItensPedido(pedido.getItens()))
        .status(pedido.getStatus().name())
        .build();
  }

  private List<InformacoesItensPedidoDTO> converterItensPedido(List<ItemPedido> itens) {
    if (CollectionUtils.isEmpty(itens)) {
      return Collections.emptyList();
    }

    return itens.stream().map(item ->
        InformacoesItensPedidoDTO
            .builder()
            .descricaoProduto(item.getProduto().getDescricao())
            .precoUnitario(item.getProduto().getPreco())
            .quantidade(item.getQuantidade())
            .build()
    ).toList();
  }

  @PatchMapping({"/{id}", "/{id}/"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateStatus(@PathVariable("id") Integer id, @RequestBody AtualizarStatusPedidoDTO dto) {
    StatusPedido status = StatusPedido.valueOf(dto.getStatus());
    pedidoService.atualizarStatus(id, status);
  }
}
