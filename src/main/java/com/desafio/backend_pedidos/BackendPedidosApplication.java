package com.desafio.backend_pedidos;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class BackendPedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendPedidosApplication.class, args);
	}

}
