package io.github.guvidaletti.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemPedidoDTO {
  private Integer produto;
  private Integer quantidade;
}
