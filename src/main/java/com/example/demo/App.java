package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

	private final PedidoService pedidoService;

	public App(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("OK!");

		String usuario = "marcio.torres@riogrande.ifrs.edu.br";

		int prod1 = 1234; // R$ 200
		int prod2 = 4567; // R$ 200

		List<Integer> codigos = List.of(prod1, prod2);

		Pedido pedido = pedidoService.submeter(usuario, codigos);
		// submeter pedido, buscar os produtos (produto não existe)
		// produto não ter estoque

		// cupons, não ter cupons (cupom valor maior que o produto)

		System.out.println(pedido.getTotal());       // 400
		System.out.println(pedido.getTotalCupons()); // 150
		System.out.println(pedido.getTotalAPagar()); // 250


	}

}
