package io.github.guvidaletti.domain.repositories;

import io.github.guvidaletti.domain.entities.Pedido;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PedidoRepository extends CrudRepository<Pedido, Integer> {

  @Fetch(FetchMode.JOIN)
  Optional<Pedido> findById(Integer id);
}
