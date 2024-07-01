package io.github.guvidaletti.domain.repositories.specs;

import io.github.guvidaletti.domain.entities.Cliente;
import org.springframework.data.jpa.domain.Specification;

public abstract class ClienteSpecs {
  public static Specification<Cliente> comNome(String nome) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
  }

  public static Specification<Cliente> comCpf(String cpf) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("cpf"), "%" + cpf + "%");
  }
}
