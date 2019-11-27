package com.kurator.library.messageProducer;

import com.kurator.library.model.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate amqpTemplate;

    @Value("${kurator.kuratorDocumentService.rabbitmq.exchange}")
    private String exchange;

    @Value("${jsa.rabbitmq.routingkey}")
    private String routingKey;

    @Value("${jsa.rabbitmq.routingkey2}")
    private String routingKey2;

    public void produceMsgFromCrawler(Message msg){
        amqpTemplate.convertAndSend(exchange, routingKey, msg);
        System.out.println("Sending in Analyzer = " +msg);
    }
    public void produceMsgFromAnalyzer(Message msg){
        amqpTemplate.convertAndSend(exchange, routingKey2, msg);
        System.out.println("Sending in relationMapper = " + msg);
    }
}
