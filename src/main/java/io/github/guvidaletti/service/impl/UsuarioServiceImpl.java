package io.github.guvidaletti.service.impl;

import io.github.guvidaletti.domain.entities.Usuario;
import io.github.guvidaletti.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Transactional
  public Usuario save(Usuario usuario) {
    return usuarioRepository.save(usuario);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usuarioRepository.findByUsuario(username).map(u -> User.builder()
            .username(u.getUsuario())
            .password(u.getSenha())
            .roles(u.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"})
            .build())
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
  }

}

/*
    return User
        .builder()
        .username("gustavo_vidaletti")
        .password(encoder.encode("teste123"))
        .roles("USER", "ADMIN")
        .build();
 */
