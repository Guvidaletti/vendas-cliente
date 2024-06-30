package io.github.guvidaletti.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

// Lombok
@Data
@NoArgsConstructor
@RequiredArgsConstructor
// JPA
@Entity
@Table(name = "cliente")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Generated
  @Column(name = "id", unique = true, updatable = false)
  private Integer id;

  @NonNull
  @Column(name = "nome", length = 100)
  @NotEmpty(message = "{campo.nome.obrigatorio}")
  private String nome;


  @NonNull
  @Column(name = "cpf", length = 11, unique = true)
  @NotEmpty(message = "{campo.cpf.obrigatorio}")
  @CPF(message = "{campo.cpf.invalido}")
  private String cpf;

  @JsonIgnore
  @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
  private Set<Pedido> pedidos;
}
