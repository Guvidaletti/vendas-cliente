package io.github.guvidaletti.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacoesPedidoDTO {
  private Integer codigo;
  private String cpf;
  private String nomeCliente;
  private String dataPedido;
  private BigDecimal total;
  private String status;
  private List<InformacoesItensPedidoDTO> itens;
}
