package io.github.guvidaletti.domain.repositories;

import io.github.guvidaletti.domain.entities.Produto;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
  List<Produto> findAll(Example<Produto> example);
}
