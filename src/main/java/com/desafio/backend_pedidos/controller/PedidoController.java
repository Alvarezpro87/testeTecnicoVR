package com.desafio.backend_pedidos.controller;

import com.desafio.backend_pedidos.model.Pedido;
import com.desafio.backend_pedidos.service.RabbitMQService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final RabbitMQService rabbitMQService;

    public PedidoController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        // Gerando ID e data de criação
        pedido.setId(UUID.randomUUID());
        pedido.setDataCriacao(String.valueOf(LocalDateTime.now()));

        // Publica o pedido na fila
        rabbitMQService.enviarPedidoParaFila(pedido);

        // Retorna o pedido criado com os dados preenchidos
        return ResponseEntity.accepted().body(pedido);
    }
}
