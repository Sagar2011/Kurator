package com.kurator.intentMapping.intentMappingProcessor;

import com.kurator.library.messageProducer.Producer;
import com.kurator.library.model.DocumentContent;
import com.kurator.library.service.DocContentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class IntentProcessor {
    @Autowired
    Producer pipeline;
    @Autowired
    DocContentService docContentService;

    @Autowired
    IRelationMappingService relationMappingService;
    public static Logger logger = LogManager.getLogger(IntentProcessor.class);

    void flowOfIntentMapping(UUID documentId) {
        DocumentContent doc = docContentService.fetchContent(documentId);
//        UUID documentId = doc.getDocumentId();

        try {
            logger.debug(doc.getTitle());
            relationMappingService.updateDocumentTitle(documentId);
//        } catch (Exception e) {
//            logger.error("error in updating documentTitle"+e.getMessage());
//        }
//        try {
            logger.debug("Before updating the semantic library");
            logger.debug(documentId);
            relationMappingService.updateDocumentInsideSemanticLibrary(documentId);
//        } catch (Exception e) {
//            logger.error("Error while updating the document inside semantic library"+e.getMessage());
//        }
//        try {
            logger.debug("Printing the message in Semantic Service");
            relationMappingService.gettingIntentFromTerms(documentId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("error while updating the Semantic Library"+e.getMessage());
//        }
//        try {
            logger.debug("Entering the function For updating intent in document database");
            relationMappingService.updateDocumentIntent(documentId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("error while updating the data in document database");
//        }
//        try {
            logger.debug("Entering the confidence rating inside semantic service");
            relationMappingService.updateConfidenceRating(documentId);
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = dateFormat.format(date);
            logger.debug("Current time of the day using Calendar - 24 hour format: " + formattedDate);

//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//        }
//        try {
            relationMappingService.updateStatus(documentId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
