package com.kurator.kuratorDocumentService.library.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${kurator.kuratorDocumentService.rabbitmq.exchange}")
	String exchange;
	
	@Value("${kurator.kuratorDocumentService.crawler.rabbitmq.queue}")
	String crawlerQueueName;

	@Value("${kurator.kuratorDocumentService.crawler.rabbitmq.routingkey}")
	private String crawlerRoutingkey;
	

	@Value("${kurator.kuratorDocumentService.firewall.rabbitmq.queue}")
	String firewallQueueName;

	@Value("${kurator.kuratorDocumentService.firewall.rabbitmq.routingkey}")
	private String firewallRoutingkey;
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Queue queue() {
		return new Queue(crawlerQueueName, false);
	}
	
	@Bean
	Binding bindingwithcrawler(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(crawlerRoutingkey);
	}
	
	@Bean
	Queue firewallQueue() {
		return new Queue(firewallQueueName, false);
	}
	
	@Bean
	Binding bindingwithfirewall(Queue firewallQueue, DirectExchange exchange) {
		return BindingBuilder.bind(firewallQueue).to(exchange).with(firewallRoutingkey);
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2JsonMessageConverter());
		return rabbitTemplate;
	}
}

