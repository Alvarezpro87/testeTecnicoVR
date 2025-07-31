package com.desafio.backend_pedidos.service;

import com.desafio.backend_pedidos.config.RabbitMQConfig;
import com.desafio.backend_pedidos.model.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarPedidoParaFila(Pedido pedido) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_PEDIDOS, pedido);
    }
}
