package io.github.guvidaletti.rest.dto;

import io.github.guvidaletti.validation.NotEmptyList;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class PedidoDTO {

  @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
  private Integer cliente;

  @NotNull(message = "{campo.total-pedido.obrigatorio}")
  private BigDecimal total;

  @NotEmptyList(message = "{campo.itens-pedido.obrigatorio}")
  private List<ItemPedidoDTO> itens;
}

