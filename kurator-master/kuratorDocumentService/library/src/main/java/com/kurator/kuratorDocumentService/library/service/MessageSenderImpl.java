package com.kurator.kuratorDocumentService.library.service;

import com.kurator.kuratorDocumentService.library.model.Document;
import com.kurator.kuratorDocumentService.library.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderImpl implements IMessageSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${kurator.kuratorDocumentService.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${kurator.kuratorDocumentService.crawler.rabbitmq.routingkey}")
	private String crawlerRoutingkey;
	
	@Value("${kurator.kuratorDocumentService.firewall.rabbitmq.routingkey}")
	private String firewallRoutingkey;
	
	public void sendToCrawler(Message msg) {
		System.out.println("in send to crawler..");
		rabbitTemplate.convertAndSend(exchange, crawlerRoutingkey, msg);
		System.out.println("Send msg = " + msg);
		System.out.println("in doc service to crawler");

	}
	public void sendToFirewall(Document doc) {
		System.out.println("in send to firewall..." + firewallRoutingkey);
		rabbitTemplate.convertAndSend(exchange, firewallRoutingkey, doc);
		System.out.println("Send msg = " + doc);
		System.out.println("in doc service to firewall");
	}

//	public void sendHelloToFirewall(String msg)
//	{
//		rabbitTemplate.convertAndSend(exchange, firewallRoutingkey, msg);
//		System.out.println("Send msg = " + msg);
//	}
//
//	public void sendHelloToCrawler(String msg)
//	{
//		rabbitTemplate.convertAndSend(exchange, crawlerRoutingkey, msg);
//		System.out.println("Send msg = " + msg);
//	}


}
