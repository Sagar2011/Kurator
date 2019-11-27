package com.kurator.analyzer.analyzerProcessor;

import com.kurator.analyzer.service.ServiceProxyImpl;
import com.kurator.library.messageProducer.Producer;
import com.kurator.library.model.Document;
import com.kurator.library.model.DocumentContent;
import com.kurator.library.model.Message;
import com.kurator.library.service.DocContentService;
import com.kurator.library.service.DocContentServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class MessageReceiver {
    @Autowired
    Producer pipeline;
    @Autowired
    DocContentService docContentService;
    @Autowired
    DocContentServiceImpl docContentServiceImpl;
    @Autowired
    ServiceProxyImpl serviceProxy;
    @Autowired
    Analyzer nlpExtractor;
    @Autowired
    TermsAndConceptsExtractor termsAndConceptsExtractor;
    @Autowired
    WeightageCalculator weightageCalculator;
    String titleFromDB;
    public ArrayList<String> titleList= new ArrayList<>();
    public ArrayList<String> headListFromDB= new ArrayList<>();
    public ArrayList<String> h2ListFromDB= new ArrayList<>();
    public ArrayList<String> h1ListFromDB= new ArrayList<>();
    public ArrayList<String> h3ListFromDB= new ArrayList<>();
    public ArrayList<String> h4ListFromDB= new ArrayList<>();
    public ArrayList<String> h5ListFromDB= new ArrayList<>();
    public ArrayList<String> h6ListFromDB= new ArrayList<>();
    public HashMap<String,Integer> termsMap= new HashMap<>();
    public HashMap<String,Integer> conceptsMap= new HashMap<>();

    public ArrayList<String> concepts= new ArrayList<>();
    public ArrayList<String> conceptsWithoutDuplicates= new ArrayList<>();

    @RabbitListener(queues="${jsa.rabbitmq.queue}")
    public void receivedMessage(Message msg) {
        System.out.println("Received Message in sidecar2: " + msg);

        DocumentContent doc = docContentService.fetchContent(msg.getDocumentId());
        try{
            titleFromDB=doc.getTitle();
            titleList.add(titleFromDB);
            headListFromDB=doc.getHeadList();
//            h1ListFromDB=doc.getH1TagList();
//            h2ListFromDB=doc.getH2TagList();
//            h3ListFromDB=doc.getH3TagList();
//            h4ListFromDB=doc.getH4TagList();
//            h5ListFromDB=doc.getH5TagList();
//            h6ListFromDB=doc.getH6TagList();
        }catch (Exception e){
            System.out.println("error in fetching content by Id");
        }
        nlpExtractor.analyzer(titleList,"terms");
        nlpExtractor.analyzer(titleList,"concepts");
        nlpExtractor.analyzer(doc.getHeadList(),"terms");
        nlpExtractor.analyzer(headListFromDB,"concepts");
//		 nlpExtractor.sentenceConverter(doc.getH1TagList(), true);
//		 nlpExtractor.sentenceConverter(doc.getH2TagList(), true);
//		 nlpExtractor.sentenceConverter(doc.getH3TagList(), true);
//		 nlpExtractor.sentenceConverter(doc.getH4TagList(), true);
//		 nlpExtractor.sentenceConverter(doc.getH5TagList(), true);
//		 nlpExtractor.sentenceConverter(doc.getH6TagList(), true);
        nlpExtractor.analyzer(doc.getContentList(),"terms");
        if(doc.getH1TagList()!=null){
            nlpExtractor.analyzer(doc.getH1TagList(),"terms");
            nlpExtractor.analyzer(doc.getH1TagList(),"concepts");
        }
        if(doc.getH2TagList()!=null){
            nlpExtractor.analyzer(doc.getH2TagList(),"terms");
            nlpExtractor.analyzer(doc.getH2TagList(),"concepts");
        }
        if(doc.getH3TagList()!=null){
            nlpExtractor.analyzer(doc.getH3TagList(),"terms");
            nlpExtractor.analyzer(doc.getH3TagList(),"concepts");
        }
        if(doc.getH4TagList()!=null){
            nlpExtractor.analyzer(doc.getH4TagList(),"terms");
            nlpExtractor.analyzer(doc.getH4TagList(),"concepts");

        }
        if(doc.getH5TagList()!=null){
            nlpExtractor.analyzer(doc.getH5TagList(),"terms");
            nlpExtractor.analyzer(doc.getH5TagList(),"concepts");

        }
        if(doc.getH6TagList()!=null){
            nlpExtractor.analyzer(doc.getH6TagList(),"terms");
            nlpExtractor.analyzer(doc.getH6TagList(),"concepts");

        }
        System.out.println(doc.getDocumentId());
        try{
            Document document= serviceProxy.gettingDocumentById(doc.getDocumentId());
            if(document.getConceptTags()!=null) {
                concepts = document.getConceptTags();
            }
        }
        catch (Exception e){
            System.out.println("error fetching Document from documentService");
        }

        try {
            System.out.println(nlpExtractor.getTerms());
            ArrayList<String> intersectionTerms = termsAndConceptsExtractor.ExtractCommonTerms(nlpExtractor.getTerms(), serviceProxy.fetchTermsFromSemanticService());
            System.out.println("intersection Terms"+intersectionTerms);
            System.out.println("CONCEPTSSSSSSSSSSSSSS   "+nlpExtractor.getConcepts());
            ArrayList<String> intersectionConcepts=termsAndConceptsExtractor.ExtractCommonConcepts(nlpExtractor.getConcepts(),serviceProxy.fetchConceptsFromSemanticService());
            System.out.println("intercectionConpert"+intersectionConcepts);
//             doc.setTermsList(intersectionTerms);
//             ArrayList<String> intersectionTerms=nlpExtractor.getTerms();
            termsMap= weightageCalculator.calculateWeightage(intersectionTerms,titleFromDB,headListFromDB,h1ListFromDB,h2ListFromDB,
                    h3ListFromDB,h4ListFromDB,h5ListFromDB,h6ListFromDB);
            conceptsMap=weightageCalculator.calculateWeightage(nlpExtractor.getConcepts(),titleFromDB,headListFromDB,h1ListFromDB,h2ListFromDB,
                    h3ListFromDB,h4ListFromDB,h5ListFromDB,h6ListFromDB);
            doc.setConceptsFromDocument(conceptsMap);
            doc.setTermsWithWeightage(termsMap);
            if(concepts!=null) {
                concepts.addAll(intersectionConcepts);
                for(String concept:concepts){
                    if(!conceptsWithoutDuplicates.contains(concept)){
                        conceptsWithoutDuplicates.add(concept);
                    }
                }
//                 Set<String> conceptSet= new LinkedHashSet<String>(concepts);
                doc.setConceptList(conceptsWithoutDuplicates);
            }

            else {
//                 Set<String> conceptSet = new LinkedHashSet<String>(intersectionConcepts);
                for(String concept:intersectionConcepts){
                    if(!conceptsWithoutDuplicates.contains(concept)){
                        conceptsWithoutDuplicates.add(concept);
                    }
                }
                doc.setConceptList(conceptsWithoutDuplicates);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in fetching data from semantic service");
        }
//         termsAndConceptsExtractor.ExtractCommonConcepts(nlpExtractor.getConcepts(), docContentServiceImpl.fetchConceptsFromSemanticService());

//         doc.setTopicsList(nlpExtractor.getTopics());
        System.out.println("Added all terms and Concepts successfully");
        doc.setIndexingStatus(DocumentContent.IndexingStatus.ANALYZED);

        docContentService.saveAllCrawlingDetails(doc);
        pipeline.produceMsgFromAnalyzer(msg);
    }

}

