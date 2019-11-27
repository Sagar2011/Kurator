package com.kurator.intentMapping.intentMappingProcessor;

import com.kurator.intentMapping.service.SemanticServiceProxy;
import com.kurator.intentMapping.service.ServiceProxyImpl;
import com.kurator.library.model.ConfidenceRating;
import com.kurator.library.model.Document;
import com.kurator.library.model.DocumentContent;
import com.kurator.library.model.SemanticDocument;
import com.kurator.library.service.DocContentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RelationMappingService implements IRelationMappingService {
    @Autowired
    DocContentServiceImpl docContentService;
    @Autowired
    SemanticServiceProxy semanticServiceProxy;
    @Autowired
    ServiceProxyImpl serviceProxy;
    @Autowired
    RestTemplate restTemplate;
    @Value("${SEMANTIC_SERVER_URL}")
    private String semanticServiceURL;
    @Value("${DOCUMENT_SERVICE_URL}")
    private String documentServiceUrl;

    public static Logger logger = LogManager.getLogger(RelationMappingService.class);


//  This function will return the document from the document Collection from document Id


    //  Updating documentTitle inside the document database
// InsideDocumentDatabase
    public void updateDocumentTitle(UUID docId) {
        Document document = serviceProxy.gettingDocumentById(docId);
        DocumentContent documentContent = docContentService.findingDocumentContentByDocId(docId);
        String titleOfDocument = documentContent.getTitle();
        HashMap<String, Integer> conceptOfDocument = documentContent.getConceptsFromDocument();
        document.setConcepts(conceptOfDocument);
        document.setTitle(titleOfDocument);
        document.setDescription(documentContent.getDescription());
        document.setIndexingStatus(Document.IndexingStatus.INDEXED);
//        document.setStatus();
        try {
            Document returnmedDocument = this.restTemplate.patchForObject(documentServiceUrl, document, Document.class);
            System.out.println();
            logger.debug("Updated successfully " + docId);
            logger.debug(returnmedDocument);
        } catch (HttpStatusCodeException e) {
            e.printStackTrace();
            logger.error("Status code exception while updating the document");
        } catch (RestClientException rcexception) {
            logger.error("The error is " + rcexception.getMessage());
//            System.out.println("No response payload exception ie. whatever id you have entered is not available in the document database");
        } catch (Exception er) {
            logger.error("Error while updating the title" + er.getMessage());
        }

    }

    //    This function is responsible to update the intent inside the document collection of the kuratorDocumentService
//InsideDocumentDatabase
    public void updateDocumentIntent(UUID docId) {
        logger.debug("Before calling the getById function inside intent document database");
        Document document = serviceProxy.gettingDocumentById(docId);
        logger.debug("After calling the getById function inside intent document da");
        DocumentContent documentContent = docContentService.findingDocumentContentByDocId(docId);
        HashMap<String, Integer> hashMapOfIntent = documentContent.getIntentList();
        System.out.println("All the intents are " + hashMapOfIntent);
        logger.debug("Before calling the patch method using resttemplate " + hashMapOfIntent);
        ArrayList<String> listOfIntent = new ArrayList<>();
        for (String key : hashMapOfIntent.keySet()) {
            listOfIntent.add(key);
        }
        logger.debug("This is the list of intent" + listOfIntent);
        document.setIntent(hashMapOfIntent);
//        System.out.println();
        logger.debug(document.getIntent());
        try {
            logger.debug("The document is " + document.getTitle());
            this.restTemplate.patchForObject(documentServiceUrl, document, Document.class);
            logger.debug("Updated successfully " + docId);
        } catch (HttpStatusCodeException error) {
            logger.debug("Status code exception while updating the document" + error.getMessage());
        } catch (RestClientException rc) {
            logger.debug("No response payload exception ie. whatever id you have entered is not available in the document database");
        }
    }

    //  This function is responsible to send the mapping of concepts inside semantic service
    public void mappingConceptsWithDocument(UUID docId) {
        DocumentContent documentContent = docContentService.fetchContent(docId);
        ArrayList<String> listOfConcepts = documentContent.getConceptList();
//        now, we will call the patch mapping from semantic service and add the listOfConcepts inside the semantic service
        try {
            this.restTemplate.patchForObject(documentServiceUrl, listOfConcepts, Document.class);
            logger.debug("Updated successfully " + docId);
        } catch (HttpStatusCodeException error) {
//            error.printStackTrace();
            logger.error("Status code exception while updating the document" + error.getMessage());
        } catch (RestClientException rc) {
            logger.error("No response payload exception ie. whatever id you have entered is not available in the document database" + rc.getMessage());
        }
    }

    //    AndUpdatingDocumentContent
    public void gettingIntentFromTerms(UUID docId) {
//           Get all the Intents from the semantic library
        ArrayList<String> listOfIntent = semanticServiceProxy.getIntents();
        logger.debug("All the intent presently inside the semantic service");
        logger.debug(listOfIntent);
//          Get all the Terms from one intent from the semantic library
        logger.debug("Before getting the documentContent ");
        DocumentContent documentContent = docContentService.fetchContent(docId);
        HashMap<String, Integer> hashMapOfCrawledTerm = documentContent.getTermsWithWeightage();
        float maxIntentWeightage = 0;
        HashMap<String, Float> proposedIntentWithWeightage = new HashMap<>();
        for (String intent : listOfIntent) {
            ArrayList<String> listOfTerms = semanticServiceProxy.getTermsWithIntents(intent);
            logger.debug("Intent " + intent + " have terms" + listOfTerms);
            float sumOfTerms = 0f;
            for (String semanticTerm : listOfTerms) {
                if (hashMapOfCrawledTerm.containsKey(semanticTerm)) {
                    sumOfTerms += hashMapOfCrawledTerm.get(semanticTerm);
                }
            }
            if (sumOfTerms > 1)
                proposedIntentWithWeightage.put(intent, sumOfTerms);
            if (sumOfTerms > maxIntentWeightage) {
                maxIntentWeightage = sumOfTerms;
            }
            logger.debug("Intent " + intent + " have weightage " + sumOfTerms);
        }
        logger.debug("Finally Mapping ");
        HashMap<String, Integer> finalIntentWithWeightage = new HashMap<>();
        for (Map.Entry mapElement : proposedIntentWithWeightage.entrySet()) {
            float rawWeight = (float) mapElement.getValue();
            float mappedWeight = rawWeight;// / maxIntentWeightage*5;
            String key = (String) mapElement.getKey();
            if (mappedWeight >= 2) {
                System.out.println("Intent " + key + "weight" + mappedWeight);

                finalIntentWithWeightage.put(key, (int) mappedWeight);
            }
        }
        documentContent.setIntentList(finalIntentWithWeightage);
        documentContent.setIndexingStatus(DocumentContent.IndexingStatus.INDEXED);
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        documentContent.setIndexedOn(localDateTime);
        System.out.println("Printing the indexing time " + documentContent.getIndexedOn());
        docContentService.saveAllCrawlingDetails(documentContent);

//          Get the intersection of the terms Present in the semantic service and our documentContent service
//          Accordingly we get the confidence rating
    }

    //    Updating the document to the semantic library
    public void updateDocumentInsideSemanticLibrary(UUID docId) {   //String documentId = docId.toString();
        Document document = serviceProxy.gettingDocumentById(docId);
        logger.debug("document going to the semantic library" + document);
        SemanticDocument semanticDocument = new SemanticDocument(document.getDocumentId(), document.getTitle(), document.getUrl(), document.getDescription(), document.getAddedBy(), document.getAddedOn(), document.getVisibility());
//        document.setVisibility(Document.Visibility.PUBLIC);
        semanticServiceProxy.addDocumentsFromIndexingService(semanticDocument);
    }

    //    Updating the intent-document confidence rating and concepts
    public void updateConfidenceRating(UUID docId) {
        DocumentContent documentContent = docContentService.findingDocumentContentByDocId(docId);
        HashMap<String, Integer> fullConcepts = documentContent.getConceptsFromDocument();
        System.out.println("Before updating Semantic library concepts " + fullConcepts);
        HashMap<String, Integer> filteredConcepts = new HashMap<>();
        ArrayList<String> conceptList = new ArrayList<>();
        for (String key : fullConcepts.keySet()) {
//            System.out.println(key + "=" + listOfTerms.get(key));
            conceptList.add(key);
        }
        for (String concept : conceptList) {
            if (fullConcepts.get(concept) >= 2 && concept.length() >= 3 && concept.contains("http") == false && concept.contains("https") == false) {
                filteredConcepts.put(concept, fullConcepts.get(concept));
            }

        }
        System.out.println("--------------------" + filteredConcepts);
        System.out.println("printing for semantic intent" + documentContent.getIntentList());
        ConfidenceRating confidenceRating = new ConfidenceRating(documentContent.getDocumentId(), documentContent.getIntentList(), filteredConcepts);
        logger.debug(confidenceRating.getConfidenceRating());
        try {
            logger.debug("The url for semantic serveer is " + "${SEMANTIC_SERVER_URL}");
            this.restTemplate.patchForObject(semanticServiceURL, confidenceRating, ConfidenceRating.class);
            logger.debug("Updated successfully " + docId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(UUID docId) {
        DocumentContent documentContent = docContentService.findingDocumentContentByDocId(docId);
        logger.debug(documentContent);
        Document document = serviceProxy.gettingDocumentById(docId);
        switch (documentContent.getIndexingStatus()) {
            case CRAWLED:
                document.setIndexingStatus(Document.IndexingStatus.CRAWLED);
            case ANALYZED:
                document.setIndexingStatus(Document.IndexingStatus.ANALYZED);
            case INDEXED:
                document.setIndexingStatus(Document.IndexingStatus.INDEXED);

        }
        try {

            this.restTemplate.patchForObject(documentServiceUrl, document, Document.class);
            logger.error("Updated successfully " + docId);
        } catch (HttpStatusCodeException error) {
            error.printStackTrace();
            logger.error("Status code exception while updating the document");
        } catch (RestClientException rc) {
            rc.printStackTrace();
            logger.error("No response payload exception ie. whatever id you have entered is not available in the document database");
        }

    }
}
