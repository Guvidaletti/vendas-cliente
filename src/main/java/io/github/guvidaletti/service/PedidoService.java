package io.github.guvidaletti.service;

import io.github.guvidaletti.domain.entities.Pedido;
import io.github.guvidaletti.domain.enums.StatusPedido;
import io.github.guvidaletti.rest.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PedidoService {
  Pedido save(PedidoDTO dto);

  Optional<Pedido> obterPedidoCompleto(Integer id);

  void atualizarStatus(Integer id, StatusPedido status);
}

