package io.github.guvidaletti.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

// Lombok
@Data
@NoArgsConstructor
// JPA
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "produto_id")
  private Produto produto;

  @NonNull
  private Integer quantidade;
}
