package com.kurator.semanticService.service;

import java.io.IOException;
import java.util.*;

import com.kurator.semanticService.exception.DocumentNotFoundException;
import com.kurator.semanticService.model.Document;
import com.kurator.semanticService.model.Concept;
import com.kurator.semanticService.model.IntentMapping;
import com.kurator.semanticService.model.ConfidenceRating;
import com.kurator.semanticService.model.ResultModel;


public interface ISemanticService {

    ArrayList<String> getLabelsWithConcepts();

    ArrayList<String> getLabelsWithTerm();

    ArrayList<String> getLabelsWithIntent();

    ArrayList<String> getAllMappedIntents(String term);

    ArrayList<String> getAllTerms(String intent);

    List<String> tokenizeSearchQuery(String searchQuery) throws IOException;

    HashMap<String, String> retrieveTermsAndTopic(String query);

    String excludeStopWords(String searcidentifyQueryhQuery) throws IOException;

    void creatingDocument(Document document);

    void updateConfidenceRating(ConfidenceRating confidenceRating);

    void mapConceptWithDocument(ConfidenceRating confidenceRating);

    Iterable<Document> getDocument(String intent, HashSet<String> conceptlist) throws DocumentNotFoundException;

    Iterable<Document> getDocumentByTerm(ArrayList<String> term, ArrayList<String> concept) throws DocumentNotFoundException;

    Iterable<Document> getDocumentBySimilarTermRelation(String term, String concept) throws DocumentNotFoundException;


    Iterable<Document> getDocumentByConcepts(String concept) throws DocumentNotFoundException;

    ArrayList<ResultModel> getRatings(ArrayList<Document> documentList, HashSet<String> concepts);

    boolean filterBadTextWithSpelling(String input) throws IOException;

    HashMap<String, ArrayList<String>> getIntentRelatedTerms();

    void createNewConcept(Concept concept);

    void createIndexedConcept(String concepts);

    void mapIntentWithTerms(IntentMapping intentMapping);

    void deleteConcept(ArrayList<String> concept);

    void deleteTerm(String term);

    public ArrayList<String> getLabelsWithReviewedConcepts();

    public ArrayList<HashMap<?, ?>> getIntentConfRating(String documentId);


}
