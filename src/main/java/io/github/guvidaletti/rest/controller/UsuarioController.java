package io.github.guvidaletti.rest.controller;

import io.github.guvidaletti.domain.entities.Usuario;
import io.github.guvidaletti.exception.SenhaInvalidaException;
import io.github.guvidaletti.rest.dto.CredenciaisDTO;
import io.github.guvidaletti.rest.dto.TokenDTO;
import io.github.guvidaletti.security.jwt.TokenService;
import io.github.guvidaletti.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioServiceImpl usuarioService;

  @PostMapping({"", "/"})
  @ResponseStatus(HttpStatus.CREATED)
  public Usuario salvar(@RequestBody @Valid Usuario usuario) {
    String senhaCriptografada = encoder.encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);
    return usuarioService.save(usuario);
  }

  @PostMapping({"/login", "/login/"})
    public TokenDTO login(@RequestBody @Valid CredenciaisDTO credenciais) {
    try {
      Usuario usuario = Usuario.builder()
          .usuario(credenciais.getLogin())
          .senha(credenciais.getSenha())
          .build();
      UserDetails details = usuarioService.autenticar(usuario);
      String token = tokenService.geraToken(usuario);
      return new TokenDTO(usuario.getUsuario(), token);
    } catch (UsernameNotFoundException | SenhaInvalidaException ex) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
  }


}
