package com.kurator.semanticService.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kurator.semanticService.model.Document;
import com.kurator.semanticService.model.Concept;
import com.kurator.semanticService.model.ConfidenceRating;
import com.kurator.semanticService.model.UserStatus;
import com.kurator.semanticService.model.ResultModel;
import com.kurator.semanticService.model.IntentMapping;

import com.kurator.semanticService.repository.ConceptRepository;
import com.kurator.semanticService.repository.DocumentRepositry;
import com.kurator.semanticService.repository.IntentRepository;
import com.kurator.semanticService.repository.TermRepository;
import com.kurator.semanticService.repository.UserStatusRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kurator.semanticService.controller.SemanticController;
import com.kurator.semanticService.exception.DocumentNotFoundException;

@Service
public class SemanticService implements ISemanticService {
    @Autowired
    DocumentRepositry documentRepository;
    @Autowired
    ConceptRepository conceptRepository;
    @Autowired
    TermRepository termRepository;
    @Autowired
    IntentRepository intentRepository;

    @Autowired
    UserStatusRepository userStatusRepository;

    @Autowired
    ResultModel resultModel;

    @Autowired
    BadWordsFilter badWordFilter;

    @Autowired
    SpellCheckService spellCheckService;


    List<String> tokenResult;

    @Autowired
    private ResourceLoader resourceLoader;

    @Transactional(readOnly = true)
    public ArrayList<String> getLabelsWithConcepts() {
        return conceptRepository.getAllConcepts();

    }

    //    @Transactional(readOnly = true)
    public ArrayList<String> getLabelsWithTerm() {
        return termRepository.getTerms();

    }

    @Transactional(readOnly = true)
    public ArrayList<String> getLabelsWithIntent() {
        return intentRepository.getAllIntents();

    }

    public ArrayList<HashMap<?, ?>> getIntentConfRating(String documentId) {
        return documentRepository.getConfidenceRating(documentId);
    }

    public UserStatus getUserByEmail(String email) {
        return userStatusRepository.findByUserEmail(email);
    }

    public void saveAbusiveUser(UserStatus userStatus) {
        userStatusRepository.save(userStatus);
    }

    /* Method to tokenize input query into terms and topic */
    public List<String> tokenizeSearchQuery(String searchQuery) throws IOException {
            String keywords = excludeStopWords(searchQuery);

            System.out.println("keywords from service: " + keywords);
            return Collections.list(new StringTokenizer(keywords, "{\":} ")).stream().map(token -> (String) token)
                    .collect(Collectors.toList());

    }

    public boolean filterBadTextWithSpelling(String input) {

        // call the bad words filter method from the badword filter class file
        return badWordFilter.filterText(input);
    }

    public HashMap<String, String> retrieveTermsAndTopic(String query) {
        try {
            List<String> identifiedQuery = tokenizeSearchQuery(query);

            ArrayList<String> terms = termRepository.getTerms();
            ArrayList<String> concepts = conceptRepository.getAllConcepts();
            ArrayList<String> intent = intentRepository.getAllIntents();

            HashMap<String, String> queryToken = new HashMap<>();
            for (String itr : identifiedQuery) {
                itr = itr + ".*";
                for (String ter : terms) {

                    boolean found = Pattern.matches(itr, ter);
                    if (found) {
                        queryToken.put(ter, "term");
                    }
                }
                for (String ter : concepts) {
                    itr = itr.replaceAll("[^a-zA-Z0-9]", "");
                    boolean found = ter.equals(itr);
                    if (found) {
                        queryToken.put(ter, "concept");
                    }
                }
                for (String i : intent) {
                    boolean found = Pattern.matches(itr, i);
                    if (found) {
                        queryToken.put("intent", i);
                    }
                }
            }
            if (queryToken.isEmpty()) {
                queryToken.put("message", "No Result Found");
            }
            return queryToken;
        } catch (Exception error) {
            error.printStackTrace();
            SemanticController.logger.error("Error Occured in Service Layer with: " + error);
            return null;
        }

    }

    /* Method to remove stop-words from query */

    public String excludeStopWords(String searcidentifyQueryhQuery) throws IOException {
        try {
            // spellCheckService.spellCheck(searcidentifyQueryhQuery);
            Resource res = resourceLoader.getResource("classpath:english_stopwords.txt");
            List<String> stopWords = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()));
            stopWords = br.lines().collect(Collectors.toList());
            ArrayList<String> allWords = Stream.of(searcidentifyQueryhQuery.split(" "))
                    .collect(Collectors.toCollection(ArrayList<String>::new));
            allWords.removeAll(stopWords);
            String result = allWords.stream().collect(Collectors.joining(" "));
            return result;
        } catch (Exception error) {
            SemanticController.logger.error("Error Occured in Service Layer with: " + error);
            return null;
        }

    }

    public void creatingDocument(Document document) {
        documentRepository.createNewDocument(document.getDocumentId(), document.getTitle(), document.getDescription(), document.getUrl(), document.getAddedBy(), document.getAddedOn(), document.getVisibility());
    }

    @Override
    public void updateConfidenceRating(ConfidenceRating confidenceRating) {
        System.out.println("updateConfidence");
        HashMap<String, Float> confidenceIntentRating = confidenceRating.getConfidenceRating();
        System.out.println(confidenceIntentRating);
        for (String intent : confidenceRating.getConfidenceRating().keySet()) {

            System.out.println(confidenceIntentRating.get(intent));
            documentRepository.updateConfidenceRating(confidenceRating.getDocumentId().toString(), intent, confidenceIntentRating.get(intent));
        }

    }

    @Override
    public void mapConceptWithDocument(ConfidenceRating confidenceRating) {
        HashMap<String, Integer> conceptLists = confidenceRating.getConceptsList();
        for (String concepts : confidenceRating.getConceptsList().keySet()) {
            System.out.println(concepts);
            conceptRepository.createIndexedConcept(concepts);
            documentRepository.mappingConcept(confidenceRating.getDocumentId().toString(), concepts, conceptLists.get(concepts));
        }

    }


    // for getting document from the intent
    public ArrayList<Document> getDocument(String intent, HashSet<String> conceptlist)
            throws DocumentNotFoundException {
        if (documentRepository.getDocumentByIntentAndConcept(intent, conceptlist).size() > 0) {
            return documentRepository.getDocumentByIntentAndConcept(intent, conceptlist);
        } else {
            throw new DocumentNotFoundException("No document found");
        }
    }

    public ArrayList<Document> getDocumentByTerm(ArrayList<String> term, ArrayList<String> concept) throws DocumentNotFoundException {
        if (documentRepository.getDocumentByTermAndConcept(term, concept).size() > 0) {
            return documentRepository.getDocumentByTermAndConcept(term, concept);
        } else {
            throw new DocumentNotFoundException("No document found");

        }
    }

    public ArrayList<String> getAllMappedIntents(String terms) {
        ArrayList<String> intentList = intentRepository.getIntentFromTerm(terms);
        System.out.println(intentList);
        return intentList;
    }

    public ArrayList<String> getAllTerms(String intent) {

        ArrayList<String> termList = intentRepository.getTermFromIntent(intent);
        return termList;
    }

    public ArrayList<String> getAllCounterTerms(String intent) {
        ArrayList<String> counterTermList = intentRepository.getCounterTermFromIntent(intent);
        return counterTermList;
    }

    public ArrayList<Document> getDocumentByConcepts(String concept) {
        int consize = documentRepository.getDocumentByConcept(concept).size();
        System.out.println(consize);
        if (consize > 0) {
            return documentRepository.getDocumentByConcept(concept);
        } else {
            return null;

        }
    }

    public ArrayList<Document> getDocumentBySimilarTermRelation(String term, String concept) throws DocumentNotFoundException {
        int consize = documentRepository.getDocumentBySimilarTermAndConcept(term, concept).size();
        System.out.println(consize);
        if (consize > 0) {
            return documentRepository.getDocumentBySimilarTermAndConcept(term, concept);
        } else {
            throw new DocumentNotFoundException("No document found");

        }
    }

    // for finding the confidence rating between intent and the documents
    public ArrayList<ResultModel> getRatings(ArrayList<Document> documentList, HashSet<String> concepts) {
        ArrayList<ResultModel> resultDocs = new ArrayList<>();
//        ArrayList<HashMap<String, Float>> confRate = new ArrayList<HashMap<String, Float>>();
        for (Document list : documentList) {
            ResultModel resultModel = new ResultModel(list.getDocumentId(), list.getTitle(), list.getDescription(),
                    list.getUrl(), list.getAddedBy(), list.getAddedOn(), list.getVisibility());
            ArrayList<HashMap<?, ?>> confRate = documentRepository.getConfidenceRating(list.getDocumentId());

            resultModel.setConfidencerating(confRate);
            resultModel.setTopicToDocument(documentRepository.getConceptWithDocument(list.getDocumentId()));
            resultDocs.add(resultModel);
        }
        return resultDocs;
    }


    //finding documents by documentId
    public ArrayList<ResultModel> getDocumentById(ArrayList<String> documentId) {
        ArrayList<ResultModel> resultDocs = new ArrayList<>();
        ArrayList<Document> docList = new ArrayList<>();
        System.out.println(documentId.toString() + "id");
        for (String idList : documentId) {
            docList.add(documentRepository.getDocumentById(idList));
            System.out.println(docList.toString() + "fron senabtuc");
        }
        for (Document list : docList) {
            ResultModel resultModel = new ResultModel(list.getDocumentId(), list.getTitle(), list.getDescription(),
                    list.getUrl(), list.getAddedBy(), list.getAddedOn());
            ArrayList<HashMap<?, ?>> confRate = documentRepository.getConfidenceRating(list.getDocumentId());
            resultModel.setTopicToDocument(documentRepository.getConceptWithDocument(list.getDocumentId()));
            resultModel.setConfidencerating(confRate);
            resultDocs.add(resultModel);
        }
        return resultDocs;
    }

    //for documentfetch by id
    public ResultModel getDoc(String documentId) {
        Document list = documentRepository.getDocumentById(documentId);
        ResultModel resultModel = new ResultModel(list.getDocumentId(), list.getTitle(), list.getDescription(),
                list.getUrl(), list.getAddedBy(), list.getAddedOn());
        ArrayList<HashMap<?, ?>> confRate = documentRepository.getConfidenceRating(list.getDocumentId());
        resultModel.setTopicToDocument(documentRepository.getConceptWithDocument(list.getDocumentId()));
        resultModel.setConfidencerating(confRate);
        return resultModel;
    }

    //for getting all intent with terms related to it
    @Override
    public HashMap<String, ArrayList<String>> getIntentRelatedTerms() {
        ArrayList<String> intents = getLabelsWithIntent();
        HashMap<String, ArrayList<String>> intentWithTerms = new HashMap<>();
        for (String list : intents) {
            ArrayList<String> terms = getAllTerms(list);
            intentWithTerms.put(list, terms);
        }
        return intentWithTerms;
    }

    public HashMap<String, ArrayList<String>> getIntentRelatedCounterTerms() {
        ArrayList<String> intents = getLabelsWithIntent();
        HashMap<String, ArrayList<String>> intentWithCounterTerms = new HashMap<>();
        for (String list : intents) {
            ArrayList<String> terms = getAllCounterTerms(list);
            intentWithCounterTerms.put(list, terms);
        }
        return intentWithCounterTerms;
    }

    // for adding the concept in the library
    @Override
    public void createNewConcept(Concept concept) {

        conceptRepository.createConcept(concept.getName(), concept.isReviewed());


    }

    @Override
    public void createIndexedConcept(String concepts) {
        conceptRepository.createIndexedConcept(concepts);
    }

    //for mapping intent with terms and counter terms
    @Override
    public void mapIntentWithTerms(IntentMapping intentMapping) {
        HashMap<String, ArrayList<String>> intentWithTerms = intentMapping.getIntentRelatedToTerms();
        HashMap<String, ArrayList<String>> intentWithCounterTerms = intentMapping.getIntentRelatedToCounterTerms();

        if (!intentWithTerms.isEmpty()) {
            for (Map.Entry<String, ArrayList<String>> entry : intentWithTerms.entrySet()) {
                ArrayList<String> terms = entry.getValue();
                for (String list : terms) {
                    intentRepository.mapIntentWithTerms(entry.getKey(), list);
                }
            }
        }
        if (!intentWithCounterTerms.isEmpty()) {
            for (Map.Entry<String, ArrayList<String>> entry : intentWithCounterTerms.entrySet()) {
                ArrayList<String> counterTerms = entry.getValue();
                for (String list : counterTerms) {
                    intentRepository.mapIntentWithCounterTerms(entry.getKey(), list);
                }
            }
        }

    }

    @Override
    public void deleteConcept(ArrayList<String> concept) {
        for (String con : concept) {
            conceptRepository.deleteImproperConcept(con);
        }
        ArrayList<String> topics = conceptRepository.getAllConcepts();
        ArrayList<String> newTopics = new ArrayList<>();
        newTopics.addAll(topics);

        for (String con : newTopics) {
            conceptRepository.createConcept(con, true);
        }

    }

    @Override
    public void deleteTerm(String term) {
        termRepository.deleteImproperTerm(term);
    }


    public ArrayList<String> getLabelsWithReviewedConcepts() {
        return conceptRepository.getReviewedConcepts();

    }


}

