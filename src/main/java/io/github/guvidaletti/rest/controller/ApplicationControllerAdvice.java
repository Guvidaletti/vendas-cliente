package io.github.guvidaletti.rest.controller;

import io.github.guvidaletti.exception.PedidoNaoEncontradoException;
import io.github.guvidaletti.exception.RegraNegocioException;
import io.github.guvidaletti.rest.ApiErrors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

  @ExceptionHandler(RegraNegocioException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
    String mensagemErro = ex.getMessage();
    return new ApiErrors(mensagemErro);
  }

  @ExceptionHandler(PedidoNaoEncontradoException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrors handlePedidoNaoEncontradoException(PedidoNaoEncontradoException ex) {
    String mensagemErro = ex.getMessage();
    return new ApiErrors(mensagemErro);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiErrors> handleResponseStatusException(ResponseStatusException ex) {
    String mensagemErro = ex.getReason();
    HttpStatusCode status = ex.getStatusCode();

    return new ResponseEntity<>(new ApiErrors(mensagemErro), status);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleException(MethodArgumentNotValidException ex) {
    return new ApiErrors(ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
  }
}
