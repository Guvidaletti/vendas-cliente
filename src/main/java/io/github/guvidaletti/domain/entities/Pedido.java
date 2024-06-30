package io.github.guvidaletti.domain.entities;

import io.github.guvidaletti.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// Lombok
@Data
@NoArgsConstructor
// JPA
@Entity
@Table(name = "pedido")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @NonNull
  @ToString.Exclude
  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @NonNull
  @Column(name = "data_pedido")
  private LocalDate dataPedido;

  @Column(name = "total", scale = 2, precision = 20)
  private BigDecimal total;

  @Enumerated(EnumType.STRING)
  private StatusPedido status;

  @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
  private List<ItemPedido> itens;
}
