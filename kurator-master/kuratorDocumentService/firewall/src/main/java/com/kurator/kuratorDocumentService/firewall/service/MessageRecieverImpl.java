package com.kurator.kuratorDocumentService.firewall.service;

import com.kurator.kuratorDocumentService.firewall.processor.IFirewallOrchestrator;
import com.kurator.kuratorDocumentService.library.model.Document;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageRecieverImpl implements IMessageReciever {
	
	@Autowired
	IFirewallOrchestrator firewallOrchestrator;

	@RabbitListener(queues = "${kurator.kuratorDocumentService.firewall.rabbitmq.queue}")
	public void recievedMessage(Document doc) {
		System.out.println("Recieved Message From RabbitMQ: " + doc);
		firewallOrchestrator.firewallCheck(doc);
		System.out.println("in doc service");
	}

//	@RabbitListener(queues = "${kurator.kuratorDocumentService.firewall.rabbitmq.queue}")
//	public void recievedHelloMessage(String msg) {
//		System.out.println("Recieved Message From RabbitMQ: " + msg);
//		firewallOrchestrator.firewallCheck(msg);
//		System.out.println("in doc service");
//	}
}
