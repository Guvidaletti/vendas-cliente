package io.github.guvidaletti.rest.controller;

import io.github.guvidaletti.domain.entities.Usuario;
import io.github.guvidaletti.service.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private final UsuarioServiceImpl usuarioService;

  @PostMapping({"", "/"})
  @ResponseStatus(HttpStatus.CREATED)
  public Usuario salvar(@RequestBody @Valid Usuario usuario) {
    String senhaCriptografada = encoder.encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);
    return usuarioService.save(usuario);
  }


}
