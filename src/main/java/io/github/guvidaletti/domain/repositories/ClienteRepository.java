package io.github.guvidaletti.domain.repositories;

import io.github.guvidaletti.domain.entities.Cliente;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

  @Fetch(FetchMode.JOIN)
  List<Cliente> findClientesByNomeContainingIgnoreCaseOrderByNomeDesc(String nome);

  @Query(" select c from Cliente c where c.nome ilike %:nome% ")
  List<Cliente> encontrarPorNome(@Param("nome") String nome);

  @Query(value = " select * from cliente c where c.nome ilike %:nome% ", nativeQuery = true)
  List<Cliente> encontrarPorNomeNativo(@Param("nome") String nome);

  boolean existsClienteByNomeContainingIgnoreCase(String nome);

  List<Cliente> findAll(Example<Cliente> example);
}
