package io.github.guvidaletti.rest.controller;

import io.github.guvidaletti.domain.entities.Produto;
import io.github.guvidaletti.domain.repositories.ProdutoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
@SecurityRequirement(name = "Token")
public class ProdutoController {

  private final ProdutoRepository produtoRepository;

  private ResponseStatusException notFound() {
    return new ResponseStatusException(HttpStatus.NOT_FOUND, "${produto.nao.encontrado}");
  }

  @GetMapping({"", "/"})
  public List<Produto> getAllProdutos(Produto produto) {
    ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    Example example = Example.of(produto, matcher);
    return produtoRepository.findAll(example);
  }

  @GetMapping({"/{id}", "/{id}/"})
  public Produto getProdutoById(@PathVariable("id") Integer id) {
    return produtoRepository.findById(id).orElseThrow(() -> notFound());
  }

  @PostMapping({"", "/"})
  @ResponseStatus(HttpStatus.CREATED)
  public Produto saveProduto(@RequestBody @Valid Produto produto) {
    return produtoRepository.save(produto);
  }

  @PutMapping({"/{id}", "/{id}/"})
  public Produto updateProduto(@PathVariable("id") Integer id, @RequestBody @Valid Produto produto) {
    return produtoRepository.findById(id).map(p -> {
      produto.setId(p.getId());
      return produtoRepository.save(produto);
    }).orElseThrow(() -> notFound());
  }

  @DeleteMapping({"/{id}", "/{id}/"})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduto(@PathVariable("id") Integer id) {
    produtoRepository.findById(id).map(p -> {
      produtoRepository.delete(p);
      return p;
    }).orElseThrow(() -> notFound());
  }

}
