package com.kurator.intentMapping.intentMappingProcessor;

import com.kurator.library.messageProducer.Producer;
import com.kurator.library.model.DocumentContent;
import com.kurator.library.model.Message;
import com.kurator.library.service.DocContentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
import java.util.UUID;

@Component
public class MessageReceiverMapping {
    @Autowired
    Producer pipeline;
    @Autowired
    DocContentService docContentService;
    public static Logger logger = LogManager.getLogger(MessageReceiverMapping.class);

    @Autowired
    RelationMappingService relationMappingService;
    @Autowired
    IntentProcessor intentProcessor;
    @RabbitListener(queues = "${jsa.rabbitmq.queue2}")
    public void receivedMessage(Message msg) {
        logger.debug("recievend in side car 3" + msg);

        DocumentContent doc = docContentService.fetchContent(msg.getDocumentId());
        UUID documentId = doc.getDocumentId();
        intentProcessor.flowOfIntentMapping(documentId);
    }
}
