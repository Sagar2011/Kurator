package com.kurator.kuratorDocumentService.firewall.service;

import com.kurator.kuratorDocumentService.library.model.Document;

public interface IFirewallService {
	
	boolean toBeCrawledOrNot(Document docById);
	boolean documentExistsOrNot(String url);
	boolean checkURL(Document doc);

}
