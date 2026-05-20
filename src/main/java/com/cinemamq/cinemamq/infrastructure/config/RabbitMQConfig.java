package com.cinemamq.cinemamq.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String FILA_INGRESSOS = "cineflow.fila.ingressos";
	public static final String EXCHANGE_INGRESSOS = "cineflow.exchange.ingressos";
	public static final String ROUTING_KEY_INGRESSOS = "cineflow.routing.key.ingressos";

	@Bean
	public Queue queue(){
		return new Queue(FILA_INGRESSOS);
	}


	@Bean
	public DirectExchange directExchange(){
		return new DirectExchange(EXCHANGE_INGRESSOS);
	}

	@Bean
	public Binding binding(Queue queue, DirectExchange directExchange){
		return BindingBuilder
						.bind(queue)
						.to(directExchange)
						.with(ROUTING_KEY_INGRESSOS);
	}


	@Bean
	public Jackson2JsonMessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}
}
