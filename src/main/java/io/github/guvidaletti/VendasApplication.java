package io.github.guvidaletti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class VendasApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext appContext = SpringApplication.run(VendasApplication.class, args);
  }
}

//  @Bean
//  public CommandLineRunner init(ClienteRepository clienteRepository) {
//    return args -> {
//      if (!clienteRepository.existsClienteByNomeContainingIgnoreCase("Gustavo Init")) {
//        clienteRepository.save(new Cliente("Gustavo Init", "12345678901"));
//      }
//    };
//  }

/*
  @Bean
  public CommandLineRunner init(@Autowired ClienteRepository clienteRepository, ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
    return args -> {
      System.out.println("ALL: " + clienteRepository.findAll());
      Cliente gustavo = new Cliente("Gustavo");
      clienteRepository.save(gustavo);

      System.out.println("aqui em: " + gustavo);

      Produto biscoito = new Produto("Biscoito", new BigDecimal(2.5));
      produtoRepository.save(biscoito);
      produtoRepository.save(new Produto("Bolacha", new BigDecimal(2.5)));

      Pedido pedido = new Pedido(gustavo, LocalDate.now());
      pedidoRepository.save(pedido);


      boolean has = clienteRepository.existsClienteByNomeContainingIgnoreCase("Gusta");
      System.out.println("Existe um Gusta? " + (has ? "Existe" : "Não"));

      List<Cliente> clientes = clienteRepository.findClientesByNomeContainingIgnoreCaseOrderByNomeDesc("Gust");

      System.out.println("Find by name: " + clientes);

      clientes.forEach(c -> {
        System.out.println(c.getId());
        System.out.println(c.getNome());
        System.out.println(c.getPedidos().stream().map(p -> ("Pedido -> ID: " + p.getId() + " nome: " + p.getCliente().getNome())).toList());
      });

      System.out.println("encontrarPorNome: " + clienteRepository.encontrarPorNome("Gus"));
      System.out.println("encontrarPorNomeNativo: " + clienteRepository.encontrarPorNomeNativo("tavo"));

      System.out.println("AllPedidos" + pedidoRepository.findAll());
    };
  }
 */
