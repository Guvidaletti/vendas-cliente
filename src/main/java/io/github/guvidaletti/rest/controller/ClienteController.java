package io.github.guvidaletti.rest.controller;

import io.github.guvidaletti.domain.entities.Cliente;
import io.github.guvidaletti.domain.repositories.ClienteRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@AllArgsConstructor
public class ClienteController {

  private ResponseStatusException notFound() {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, "{cliente.nao.encontrado}");
  }

  private final ClienteRepository clienteRepository;

  @GetMapping({"", "/"})
  public List<Cliente> getAllClientes(Cliente cliente) {
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    Example example = Example.of(cliente, matcher);
    return clienteRepository.findAll(example);
  }

  @GetMapping({"/{id}", "/{id}/"})
  public Cliente getClienteById(@PathVariable("id") Integer id) {
    return clienteRepository.findById(id).orElseThrow(() -> notFound());
  }

  @PostMapping({"", "/"})
  @ResponseStatus(HttpStatus.CREATED)
  public Cliente saveCliente(@RequestBody @Valid Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @PutMapping({"/{id}", "/{id}/"})
  public Cliente updateCliente(@PathVariable("id") Integer id, @RequestBody @Valid Cliente cliente) {
    System.out.println("{cliente.nao.encontrado}");

    return clienteRepository.findById(id).map(c -> {
      cliente.setId(c.getId());
      return clienteRepository.save(cliente);
    }).orElseThrow(() -> notFound());
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCliente(@PathVariable("id") Integer id) {
    clienteRepository.findById(id).map(c -> {
      clienteRepository.delete(c);
      return c;
    }).orElseThrow(() -> notFound());
  }
}


/*
  public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Integer id) {
    //outra opcao
    //return ResponseEntity.of(clienteRepository.findById(id));
    Optional<Cliente> cliente = clienteRepository.findById(id);

    if (cliente.isPresent()) {
      return ResponseEntity.ok(cliente.get());
    }

    return ResponseEntity.notFound().build();
  }
 */