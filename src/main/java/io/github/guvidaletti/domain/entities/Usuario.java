package io.github.guvidaletti.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

// Lombok
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
// JPA
@Entity
@Table(name = "usuario")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, updatable = false)
  private Integer id;

  @NonNull
  @NotEmpty(message = "{campo.username.obrigatorio}")
  @Column(name = "usuario")
  private String usuario;

  @NonNull
  @NotEmpty(message = "{campo.password.obrigatorio}")
  @Column(name = "senha")
  private String senha;

  @Column(name = "admin")
  private boolean admin;
}
