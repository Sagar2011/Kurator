//package com.kurator.semanticService.service;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import com.kurator.semanticService.kuratorSemanticServiceApplication;
//import com.kurator.semanticService.model.Intent;
//import com.kurator.semanticService.model.Term;
//import com.kurator.semanticService.model.Topic;
//import com.kurator.semanticService.repository.IntentRepository;
//import com.kurator.semanticService.repository.TermRepository;
//import com.kurator.semanticService.repository.TopicRepository;
//
//@SpringBootTest(classes = kuratorSemanticServiceApplication.class)
//@TestPropertySource("/test.properties")
//class SemanticServiceTest {
//
//    @Autowired
//    private TopicRepository topicRepository;
//
//    @Autowired
//    private TermRepository termRepository;
//
//    @Autowired
//    private IntentRepository intentRepository;
//
//    @Autowired
//    private SemanticService semanticService;
//
//    private static Topic topic;
//    private static Term term;
//    private static Intent intent;
//
//    @BeforeAll
//    public static void setup() {
//        topic = new Topic("test", 100);
//        intent = new Intent("intent_test", 200);
//        term = new Term("term_test", 300);
//    }
//
//    @Test
//    void testTopicNodes() {
//        topicRepository.save(topic);
//        assertTrue(semanticService.getLabelsWithTopic().contains("test"));
//    }
//
//    @Test
//    void testIntentNodes() {
//        intentRepository.save(intent);
//        assertTrue(semanticService.getLabelsWithIntent().contains("intent_test"));
//    }
//
//    @Test
//    void testTermNodes() {
//        termRepository.save(term);
//        assertTrue(semanticService.getLabelsWithTerm().contains("term_test"));
//    }
//
//    //testing the parsed query is right or not
//    @Test
//    void testretrieveTermsAndTopic() {
//        assertNotNull(semanticService.retrieveTermsAndTopic("test the data"));
//    }
//
//}
