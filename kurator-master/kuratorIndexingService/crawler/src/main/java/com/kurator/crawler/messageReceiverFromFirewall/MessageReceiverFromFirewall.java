package com.kurator.crawler.messageReceiverFromFirewall;

import com.kurator.crawler.crawlerProcessor.ICrawler;
import com.kurator.library.messageProducer.Producer;
import com.kurator.library.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class MessageReceiverFromFirewall {
    @Autowired
    ICrawler crawler;
    @Autowired
    Producer pipeline;

    @RabbitListener(queues="${kurator.kuratorDocumentService.crawler.rabbitmq.queue}")
    public void receivedMessage(Message msg) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
        System.out.print(msg.getUrl());
        crawler.crawl(msg);
        pipeline.produceMsgFromCrawler(msg);
    }
}