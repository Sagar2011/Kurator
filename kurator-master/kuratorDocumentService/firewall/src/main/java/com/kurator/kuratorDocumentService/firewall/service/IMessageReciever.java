package com.kurator.kuratorDocumentService.firewall.service;

import com.kurator.kuratorDocumentService.library.model.Document;

public interface IMessageReciever {
	
	void recievedMessage(Document doc);

}
