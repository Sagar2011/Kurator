package com.kurator.kuratorDocumentService.firewall.processor;

import com.kurator.kuratorDocumentService.library.model.Document;

public interface IFirewallOrchestrator {
	
	void firewallCheck(Document doc);

}
