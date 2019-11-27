package com.kurator.intentMapping.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${jsa.rabbitmq.queue}")
    private String queueName;

    @Value("${kurator.kuratorDocumentService.rabbitmq.exchange}")
    private String exchange;

    @Value("${kurator.kuratorDocumentService.crawler.rabbitmq.queue}")
    String crawlerQueueName;

    @Value("${kurator.kuratorDocumentService.crawler.rabbitmq.routingkey}")
    private String crawlerRoutingkey;

    @Value("${jsa.rabbitmq.routingkey}")
    private String routingKey;

    @Value("${jsa.rabbitmq.queue2}")
    private String queueName2;

    @Value("${jsa.rabbitmq.routingkey2}")
    private String routingKey2;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    @Bean
    Queue queue2() {
        return new Queue(queueName2, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue documentServiceQueue() {
        return new Queue(crawlerQueueName, false);
    }

    @Bean
    Binding bindingwithcrawler(Queue documentServiceQueue, DirectExchange exchange) {
        return BindingBuilder.bind(documentServiceQueue).to(exchange).with(crawlerRoutingkey);
    }
//   @Bean
//   public Exchange fanoutExchange(@Value("${tutorial.exchange.name}") final String exchangeName) {
//       return new FanoutExchange(exchangeName);
//   }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }
    @Bean
    Binding binding2(Queue queue2, DirectExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with(routingKey2);
    }
    //   @Bean
//   public Queue queue1(@Value("${tutorial.queue.name1}") final String queueName) {
//    return new Queue(queueName);
//   }
//
//   @Bean
//   public Queue queue2(@Value("${tutorial.queue.name2}") final String queueName) {
//    return new Queue(queueName);
//   }
//
//   @Bean
//   public Binding binding1(final Queue queue1, final Exchange fanoutExchange) {
//    return BindingBuilder
//    .bind(queue1).to(fanoutExchange).with("*").noargs();
//   }
//
//   @Bean
//   public Binding binding2(final Queue queue2, final Exchange fanoutExchange) {
//    return BindingBuilder
//    .bind(queue2).to(fanoutExchange).with("*").noargs();
//   }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}

