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
    public static final String DLQ_PEDIDOS = "pedidos.entrada.alexandre.dlq";
    public static final String EXCHANGE = "pedidos.exchange.alexandre";
    public static final String ROUTING_KEY = "pedidos.entrada.alexandre";

    @Bean
    public Queue queuePedidos() {
        return QueueBuilder.durable(FILA_PEDIDOS)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", DLQ_PEDIDOS)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ_PEDIDOS).build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding pedidosBinding(Queue queuePedidos, TopicExchange exchange) {
        return BindingBuilder.bind(queuePedidos).to(exchange).with(ROUTING_KEY);
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