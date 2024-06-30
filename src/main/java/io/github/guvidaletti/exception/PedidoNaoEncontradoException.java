package io.github.guvidaletti.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
  public PedidoNaoEncontradoException() {
    super("${pedido.nao.encontrado}");
  }
}
