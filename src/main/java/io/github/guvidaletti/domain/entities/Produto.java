package io.github.guvidaletti.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

// Lombok
@Data
@NoArgsConstructor
// JPA
@Entity
@Table(name = "produto")
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Integer id;

  @NonNull
  @Column(name = "descricao")
  @NotEmpty(message = "{campo.descricao.obrigatorio}")
  private String descricao;

  @NonNull
  @Column(name = "preco_unitario", scale = 2, precision = 20)
  @NotNull(message = "{campo.preco.obrigatorio}")
  private BigDecimal preco;

  @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
  private List<ItemPedido> itens;
}
