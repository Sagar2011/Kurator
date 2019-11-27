package com.kurator.semanticService.controller;

import java.io.IOException;
import java.util.*;

import com.kurator.semanticService.model.Document;
import com.kurator.semanticService.model.Concept;
import com.kurator.semanticService.model.IntentMapping;
import com.kurator.semanticService.model.ResultModel;
import com.kurator.semanticService.model.ResponseModel;
import com.kurator.semanticService.model.ConfidenceRating;
import com.kurator.semanticService.model.UserStatus;


import io.swagger.annotations.ApiModelProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.kurator.semanticService.exception.DocumentNotFoundException;
import com.kurator.semanticService.service.SemanticService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class
SemanticController {
    @Autowired
    SemanticService semanticService;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    UserServiceProxy userServiceProxy;

    public static Logger logger = LogManager.getLogger(SemanticController.class);

    /* Method to return documents namely terms, intents and topic from the query */
    @GetMapping("/documents")
    public ResponseEntity<?> identifiedDocuments(@RequestParam("query") String query, @RequestParam("email") String email) throws IOException {
        HashMap<String, String> keywordsRetrieved = new HashMap<>();
        keywordsRetrieved.clear();
        try {
            String concept = " ";
            String intent = null;
            ArrayList<String> term = new ArrayList<>();
            int size;
            if (semanticService.filterBadTextWithSpelling(query)) {
                if (semanticService.getUserByEmail(email) == null) {
                    UserStatus userStatus = new UserStatus();
                    userStatus.setAbusiveHitCounter(1);
                    userStatus.setUserEmail(email);
                    userStatus.setCreatedAt(new Date());
                    semanticService.saveAbusiveUser(userStatus);

                    responseModel.setStatusCode(406);
                    responseModel.setMessage("Sorry not allowed");
                    responseModel.setResult(null);
                    return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
                } else {
                    UserStatus userStatus = semanticService.getUserByEmail(email);
                    if (userStatus.getAbusiveHitCounter() < 3) {
                        userStatus.setCreatedAt(new Date());
                        userStatus.setAbusiveHitCounter(userStatus.getAbusiveHitCounter() + 1);
                        semanticService.saveAbusiveUser(userStatus);
                        responseModel.setStatusCode(406);
                        responseModel.setMessage("Sorry not allowed");
                        responseModel.setResult(null);
                        return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
                    } else {
                        //call the user service for the blocking of user
                        userServiceProxy.markUserStatus(email);
                        semanticService.saveAbusiveUser(userStatus);
                        responseModel.setStatusCode(423);
                        responseModel.setMessage("Sorry you are blocked");
                        responseModel.setResult(null);
                        return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
                    }
                }
            }


            keywordsRetrieved = semanticService.retrieveTermsAndTopic(query);
            keywordsRetrieved.forEach((key, value) -> {
                if (value.equals("term")) {
                    System.out.println(key + "key" + value);
                    term.add(key);
                }
            });
            HashSet<String> topicFromQuery = new HashSet<>();
            ArrayList<Document> list = new ArrayList<Document>();
            intent = keywordsRetrieved.get("intent");
            keywordsRetrieved.forEach((key, value) -> {
                if (value.equals("concept")) {
                    topicFromQuery.add(key);
                }
            });
            size = topicFromQuery.size();
            // for now if topic is only one for the term search
            if (size >= 1) {
                System.out.println("inside the one topic");
                concept = topicFromQuery.stream().findFirst().get();
                System.out.println(concept + "controller concept");

            } else {
                concept = " ";
            }

            if (intent != null && topicFromQuery.size() > 0) {
                list = (ArrayList<Document>) semanticService.getDocument(intent, topicFromQuery);

            } else if (term.size() > 0 && topicFromQuery.size() > 0) {
                ArrayList topics = new ArrayList(topicFromQuery);
                list = (ArrayList<Document>) semanticService.getDocumentByTerm(term, topics);

            } else if (!(concept.isBlank())) {
                System.out.println("concept--------------");
                list = (ArrayList<Document>) semanticService.getDocumentByConcepts(concept);
                System.out.println(list);
            } else {
                logger.debug("bad request happend at controller else case");
            }
            ArrayList<ResultModel> resultDocuments = semanticService.getRatings(list, topicFromQuery);
            responseModel.setStatusCode(200);
            responseModel.setMessage("Document founded");
            responseModel.setResult(resultDocuments);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        } catch (DocumentNotFoundException error) {
            responseModel.setStatusCode(404);
            responseModel.setMessage("No Document Found");
            responseModel.setResult(null);
            logger.error("Exception Occured with message at identifiedDocuments method" + error);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        } catch (DatabaseException exceptionDb) {
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            logger.error("Exception Occured with message at identifiedDocuments method" + exceptionDb);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }

    }

    @GetMapping("/concepts")
    public ArrayList<String> getConcepts() {
        try {

            ArrayList<String> to = semanticService.getLabelsWithConcepts();

            return to;
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            return null;
        }
    }

    @GetMapping("/concepts/user")
    public ArrayList<String> getReviewedConcepts() {
        try {
            ArrayList<String> con = semanticService.getLabelsWithReviewedConcepts();
            return con;
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            return null;
        }
    }


    @GetMapping("/terms")
    public ArrayList<String> getTerms() {
        try {

            ArrayList<String> term = semanticService.getLabelsWithTerm();

            return term;
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            return null;
        }
    }

    @ApiModelProperty("intent mapping service returns intent related to terms")
    @GetMapping("/intent/confRating")
    public ArrayList<HashMap<?, ?>> getMaxIntent(@RequestParam String documentId) {
        return semanticService.getIntentConfRating(documentId);

    }


    // for intent mapping service returns intent related to terms
    @ApiModelProperty("intent mapping service returns intent related to terms")
    @GetMapping("/contexts")
    public HashMap<String, ArrayList<String>> getCommonIntentWithTerms(@RequestParam ArrayList<String> terms) {
        System.out.println(semanticService.getLabelsWithIntent());
//        ArrayList<String> terms=new ArrayList<>();
        System.out.println("entering the api");
//        terms.add("what");

        System.out.println("Printing the terms for testing");
        System.out.println(terms);
        HashMap<String, ArrayList<String>> intentWithTerms = new HashMap<>();
        System.out.println("No error");
        ArrayList<String> IntentList = new ArrayList<>();
//            IntentList.add(terms.get(0));
        for (String oneTerm : terms) {
            System.out.println("printing first" + oneTerm);
            ArrayList<String> IntentListForOneTerm = semanticService.getAllMappedIntents(oneTerm);
            for (String proposedIntent : IntentListForOneTerm) {
                if (IntentList.contains(proposedIntent) == false) {
                    IntentList.add(proposedIntent);
                }
            }
        }
//            IntentList.add("introduction");
//            IntentList.add("advanced details");
        System.out.println("After listOFintent");
        for (String intent : IntentList) {
            ArrayList<String> allTerms = semanticService.getAllTerms(intent);
            ArrayList<String> commonTerms = new ArrayList<>();
            for (String proposedTerm : allTerms) {
                if (terms.contains(proposedTerm))
                    commonTerms.add(proposedTerm);
            }
            intentWithTerms.put(intent, commonTerms);
        }
//            ArrayList<String> termList = new ArrayList<>();

//            IntentList.put("IntentList", termList);
        return intentWithTerms;
    }

    // add new documents form the indexing pipeline
    @ApiModelProperty("add new documents form the indexing pipeline")
    @PostMapping("/documents")
    public ResponseEntity<?> addDocumentsFromIndexingService(@RequestBody Document document) {
        System.out.println("Posting the document");
        System.out.println(document);
        try {


            semanticService.creatingDocument(document);
            responseModel.setStatusCode(200);
            responseModel.setMessage("Document added succesfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);


        } catch (
                Exception exception) {
            logger.error("Occured at method in addDocumentsFromIndexingService with message" + exception);
            responseModel.setStatusCode(500);
            responseModel.setMessage("Server not responding");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    //for patching the confidence rating with the intent mapping from indexing service
    @ApiModelProperty("patching the confidence rating with the intent mapping from indexing service")
    @PatchMapping("/confidenceratings")
    public ResponseEntity<?> updateConfidenceRatingWithIntent(@RequestBody ConfidenceRating confidenceIntentRating) {
        System.out.println(confidenceIntentRating.toString());
        try {
            semanticService.updateConfidenceRating(confidenceIntentRating);
            semanticService.mapConceptWithDocument(confidenceIntentRating);
            responseModel.setStatusCode(200);
            responseModel.setMessage("Intent mapped with documents succesfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        } catch (Exception exception) {
            logger.error("Occured at method updateConfidenceRatingWithIntent with message" + exception);
            responseModel.setStatusCode(500);
            responseModel.setMessage("Server not responding");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }


    // for the guest session
    @ApiModelProperty("guest session")
    @GetMapping("guest/documents")
    public ResponseEntity<?> guestUsefulDocuments(@RequestParam("query") String query) {
        HashMap<String, String> keywordsRetrieved = new HashMap<>();
        keywordsRetrieved.clear();
        try {
            String concept = " ";
            String intent = null;
            ArrayList<String> term = new ArrayList<>();
            int size;
            if (semanticService.filterBadTextWithSpelling(query)) {
                responseModel.setStatusCode(406);
                responseModel.setMessage("Sorry not allowed");
                responseModel.setResult(null);
                return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
            }
            keywordsRetrieved = semanticService.retrieveTermsAndTopic(query);
            HashSet<String> topicFromQuery = new HashSet<>();
            ArrayList<Document> list = new ArrayList<Document>();
            keywordsRetrieved.forEach((key, value) -> {
                if (value.equals("term")) {
                    System.out.println(key + "key" + value);
                    term.add(key);
                }
            });
            intent = keywordsRetrieved.get("intent");
            keywordsRetrieved.forEach((key, value) -> {
                if (value.equals("concept")) {
                    topicFromQuery.add(key);
                }
            });
            size = topicFromQuery.size();
            // for now if topic is only one for the term search
            if (size >= 1) {
                System.out.println("inside the one topic");
                concept = topicFromQuery.stream().findFirst().get();
                System.out.println(concept + "controller concept");

            } else {
                concept = " ";
            }

            if (intent != null && topicFromQuery.size() > 0) {
                list = (ArrayList<Document>) semanticService.getDocument(intent, topicFromQuery);

            } else if (term.size() > 0 && topicFromQuery.size() > 0) {
                ArrayList<String> topics = new ArrayList<>(topicFromQuery);
                list = (ArrayList<Document>) semanticService.getDocumentByTerm(term, topics);

            } else if (!(concept.isBlank())) {
                System.out.println("concept--------------");
                list = (ArrayList<Document>) semanticService.getDocumentByConcepts(concept);
                System.out.println(list);
            } else {
                logger.debug("bad request happend at controller else case");
            }
            ArrayList<ResultModel> resultDocuments = semanticService.getRatings(list, topicFromQuery);
            responseModel.setStatusCode(200);
            responseModel.setMessage("Document founded");
            responseModel.setResult(resultDocuments);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        } catch (
                DocumentNotFoundException error) {
            responseModel.setStatusCode(404);
            responseModel.setMessage("No Document Found");
            responseModel.setResult(null);
            logger.error("Exception Occured with message at identifiedDocuments method" + error);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        } catch (
                DatabaseException exceptionDb) {
            responseModel.setStatusCode(500);
            responseModel.setMessage("INTERNAL SERVER ERROR");
            responseModel.setResult(null);
            logger.error("Exception Occured with message at identifiedDocuments method" + exceptionDb);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }

    }

    // for the user service to get documents by document id
    @GetMapping("/documents/users")
    public ArrayList<ResultModel> getDocuments(@RequestParam ArrayList<String> documentId) {
        try {
            return semanticService.getDocumentById(documentId);
        } catch (
                DatabaseException exceptionDb) {

            logger.error("Exception Occured with message at identifiedDocuments method" + exceptionDb);
            return null;
        }
    }

    // for calling the documentBy id
    @GetMapping("/documents/{documentId}")
    public ResultModel getDocument(@PathVariable String documentId) {
        try {
            return semanticService.getDoc(documentId);
        } catch (
                DatabaseException exceptionDb) {

            logger.error("Exception Occured with message at identifiedDocuments method" + exceptionDb);
            return null;


        }
    }

    // admin for getting intent connected with terms
    @GetMapping("/intents")
    public ResponseEntity<ResponseModel> getIntentWIthTerms() {
        try {
            ArrayList<HashMap<String, ArrayList<String>>> resultedIntent = new ArrayList<>();

            resultedIntent.add(semanticService.getIntentRelatedTerms());
            if (resultedIntent.size() > 0) {

                responseModel.setStatusCode(200);
                responseModel.setMessage("Intent found with terms");
                responseModel.setResult(resultedIntent);
                return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
            } else {
                responseModel.setStatusCode(404);
                responseModel.setMessage("No intent found");
                responseModel.setResult(null);
                return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
            }
        } catch (Exception exception) {
            logger.error("Occured at method in getIntentWithTerms with message" + exception);
            responseModel.setStatusCode(500);
            responseModel.setMessage("Server not responding");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

//    //for adding new intent to the semantic library
//
//
//
//   public ResponseEntity<?> addintent(@RequestParam String intent) {
//
//        return null;
//    }

    //for adding new concepts from the admin dashboard
    @PostMapping("/concepts")
    public ResponseEntity<?> addConcept(@RequestBody ArrayList<Concept> concept) {
        try {
            for (Concept list : concept) {
                semanticService.createNewConcept(list);

            }
            responseModel.setStatusCode(200);
            responseModel.setMessage("Concept added succesfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        } catch (
                Exception exception) {
            logger.error("Occured at method in addConcept with message" + exception);
            responseModel.setStatusCode(500);
            responseModel.setMessage("Server not responding");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    // admin api for mapping intent with terms and counter terms
    @PatchMapping("/intents/terms")
    public ResponseEntity<?> intentWithTerms(@RequestBody IntentMapping intentMapping) {
        try {
            semanticService.mapIntentWithTerms(intentMapping);
            responseModel.setStatusCode(200);

            responseModel.setMessage("intent mapped successfully");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);

        } catch (
                Exception exception) {
            logger.error("Occured at method in mapIntentWithTerms with message" + exception);
            responseModel.setStatusCode(500);
            responseModel.setMessage("Server not responding");
            responseModel.setResult(null);
            return new ResponseEntity<ResponseModel>(responseModel, HttpStatus.OK);
        }
    }

    @GetMapping("/intents/terms")
    public ArrayList<String> getTermsWithIntents(@RequestParam String intent) {
        return semanticService.getAllTerms(intent);
    }

    @GetMapping("/intents/terms/admin")
    public IntentMapping getTermsWithIntentsAdmin() {
        IntentMapping intentMapping = new IntentMapping();
        intentMapping.setIntentRelatedToTerms(semanticService.getIntentRelatedTerms());
        intentMapping.setIntentRelatedToCounterTerms(semanticService.getIntentRelatedCounterTerms());
        return intentMapping;
    }

    @GetMapping("/intents/index")
    public ArrayList<String> getIntents() {
        try {
            ArrayList<String> intents = semanticService.getLabelsWithIntent();
            return intents;
        } catch (Exception error) {
            logger.error("Error happened with message" + error);
            return null;
        }
    }

    @GetMapping("/concept/documents")
    public ArrayList<ResultModel> getDocumentByConcept(@RequestParam String concept) {
        try {
            ArrayList<Document> list = semanticService.getDocumentByConcepts(concept);
            HashSet<String> concepts = new HashSet<>();
            concepts.add(concept);
            ArrayList<ResultModel> resultDocuments = semanticService.getRatings(list, concepts);
            return resultDocuments;
        } catch (Exception error) {
            logger.error("Occured at method getDocumentByConcept with message" + error);
            return null;
        }

    }

    @DeleteMapping("/concept/admin")
    public void removeImproperConcept(@RequestParam ArrayList<String> concept) {
        semanticService.deleteConcept(concept);
    }

    @DeleteMapping("/term/admin")
    public void removeImproperTerm(@RequestParam String term) {
        semanticService.deleteTerm(term);
    }


}
