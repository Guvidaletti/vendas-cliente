package io.github.guvidaletti.domain.repositories;

import io.github.guvidaletti.domain.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
  Optional<Usuario> findByUsuario(String usuario);
}
