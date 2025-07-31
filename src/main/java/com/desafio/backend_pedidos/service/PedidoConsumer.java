package com.desafio.backend_pedidos.service;

import com.desafio.backend_pedidos.config.RabbitMQConfig;
import com.desafio.backend_pedidos.model.Pedido;
import com.desafio.backend_pedidos.model.StatusPedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PedidoConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();
    private static final Map<UUID, String> statusPedidos = new ConcurrentHashMap<>();

    public PedidoConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Método para atualizar status
    public static void atualizarStatus(UUID id, String status) {
        statusPedidos.put(id, status);
    }

    //Método Consultar Status

    public static String consultarStatus(UUID id) {
        return statusPedidos.getOrDefault(id, "NÃO ENCONTRADO");
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_PEDIDOS)
    public void processarPedido(Pedido pedido) {
        System.out.println("📥 Pedido recebido da fila: " + pedido.getId());
        atualizarStatus(pedido.getId(), "PROCESSANDO");

        try {
            Thread.sleep(2000);

            if (random.nextDouble() < 0.2) {
                throw new RuntimeException("Erro simulado no processamento do pedido");
            }

            StatusPedido status = new StatusPedido(pedido.getId(), "SUCESSO", null);
            atualizarStatus(pedido.getId(), "SUCESSO");
            rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_STATUS_SUCESSO, status);
            System.out.println("✅ Pedido processado com SUCESSO!");

        } catch (Exception e) {
            atualizarStatus(pedido.getId(), "FALHA");
            StatusPedido status = new StatusPedido(pedido.getId(), "FALHA", e.getMessage());
            rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_STATUS_FALHA, status);
            System.err.println("❌ Falha no processamento: " + e.getMessage());
        }
    }
}
