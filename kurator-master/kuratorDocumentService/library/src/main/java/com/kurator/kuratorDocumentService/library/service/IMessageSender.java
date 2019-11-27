package com.kurator.kuratorDocumentService.library.service;

import com.kurator.kuratorDocumentService.library.model.Document;
import com.kurator.kuratorDocumentService.library.model.Message;

public interface IMessageSender {
	
	void sendToCrawler(Message msg);
	void sendToFirewall(Document doc);

}
