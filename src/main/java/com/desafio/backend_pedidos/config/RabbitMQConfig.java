package com.desafio.backend_pedidos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FILA_PEDIDOS = "pedidos.entrada.alexandre";
    public static final String FILA_PEDIDOS_DLQ = "pedidos.entrada.alexandre.dlq";
    public static final String FILA_STATUS_SUCESSO = "pedidos.status.sucesso.alexandre";
    public static final String FILA_STATUS_FALHA = "pedidos.status.falha.alexandre";
    public static final String EXCHANGE_PEDIDOS = "pedidos.exchange.alexandre";

    @Bean
    public Queue queuePedidos() {
        return new Queue(FILA_PEDIDOS, true);
    }

    @Bean
    public Queue queuePedidosDlq() {
        return new Queue(FILA_PEDIDOS_DLQ, true);
    }

    @Bean
    public Queue queueStatusSucesso() {
        return new Queue(FILA_STATUS_SUCESSO, true);
    }

    @Bean
    public Queue queueStatusFalha() {
        return new Queue(FILA_STATUS_FALHA, true);
    }

    // ✅ Criando o Exchange
    @Bean
    public DirectExchange pedidosExchange() {
        return new DirectExchange(EXCHANGE_PEDIDOS);
    }

    // ✅ Binding da fila com o Exchange e routing key
    @Bean
    public Binding bindingPedidos(Queue queuePedidos, DirectExchange pedidosExchange) {
        return BindingBuilder.bind(queuePedidos)
                .to(pedidosExchange)
                .with("pedidos.entrada.alexandre");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
