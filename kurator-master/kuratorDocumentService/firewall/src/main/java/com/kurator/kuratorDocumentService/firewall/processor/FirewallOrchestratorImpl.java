package com.kurator.kuratorDocumentService.firewall.processor;

import com.kurator.kuratorDocumentService.firewall.service.IFirewallService;
import com.kurator.kuratorDocumentService.library.model.Document;
import com.kurator.kuratorDocumentService.library.model.Message;
import com.kurator.kuratorDocumentService.library.model.ResponseModel;
import com.kurator.kuratorDocumentService.library.model.Status;
import com.kurator.kuratorDocumentService.library.model.Status.Reason;
import com.kurator.kuratorDocumentService.library.model.Status.Validity;
import com.kurator.kuratorDocumentService.library.service.IDocumentService;
import com.kurator.kuratorDocumentService.library.service.IMessageSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class FirewallOrchestratorImpl implements IFirewallOrchestrator {

    @Autowired
    IDocumentService documentService;

    @Autowired
    IFirewallService firewallService;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    IMessageSender messageSender;

//	String url;
//	UUID documentId=UUID.fromString("1c2ada99-fe93-4fd9-8bf0-3f4013c64403");
//	String msg = messageReciever.firewallInput();// will contain url and docId

    public static Logger logger = LogManager.getLogger(FirewallOrchestratorImpl.class);

    public void firewallCheck(Document doc) {

        String url = doc.getUrl();

//		Document docById = documentService.findById(documentId);
        if (firewallService.checkURL(doc)) {
            if (firewallService.documentExistsOrNot(url)) {
                ArrayList<Document> docByUrl = documentService.findByUrl(url);
                System.out.println("document searched by url......");
                System.out.println(docByUrl);
                Document existingDoc = docByUrl.get(0);
                if (firewallService.toBeCrawledOrNot(existingDoc)) {
                    existingDoc.setUpdatedBy(doc.getAddedBy());
                    existingDoc.setUpdatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
                    documentService.saveDocument(existingDoc);//existing document gets updated
                    UUID documentId = existingDoc.getDocumentId();//existing document retains its id after re-crawl
                    Message msg = new Message(documentId, url);
                    messageSender.sendToCrawler(msg);
                }
            } // if the document is valid and duplicate and has not been updated recently, it is sent to crawling queue
            else {
                Validity validity = Validity.Valid;
                Reason reason = Reason.NotDuplicate;
                Status status = new Status(validity, reason);
                doc.setStatus(status);

                doc.setAddedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
//                System.out.println("time::" + LocalDateTime.now());
//                System.out.println(doc.getAddedOn());
                doc.setUpdatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
                documentService.saveDocument(doc);//new document gets saved
                System.out.println(documentService.findById(doc.getDocumentId()) + "checking form db");
                UUID documentId = doc.getDocumentId();//generated id is sent to crawler for new document
                Message msg = new Message(documentId, url);
                messageSender.sendToCrawler(msg);
            } // else if the document is valid and not duplicate it will get sent to crawling
            // queue

        }
//		else 
//		{
//			firewallService.causeOfInvalidity(doc);//notification service later picks up the invalid documents and sends corresponding notifictions to the user who added it
//		} // else if the document is invalid, it should not be crawled, document status set in service class
    }
}
