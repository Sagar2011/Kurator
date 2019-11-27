package com.kurator.analyzer.analyzerProcessor;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Analyzer {
    StanfordCoreNLP stanfordCoreNLP= NlpPipeline.getPipeline();
    List<String> sentenceList= new LinkedList<String>();
    HashMap<String, String> partsOfSpeechMap= new HashMap<String, String>();
    ArrayList<String> terms= new ArrayList<String>();
    ArrayList<String> concepts = new ArrayList<String>();
    List<String> lemmas= new ArrayList<String>();
    HashMap<String, Integer> wordCount = new HashMap<String, Integer>();

    public void analyzer(List<String> headlist, String field ) {
        for(String item:headlist) {
            CoreDocument coreDocument= new CoreDocument(item);
            stanfordCoreNLP.annotate(coreDocument);
            List<CoreSentence> sentences= coreDocument.sentences();
            for(CoreSentence sentence:sentences) {
                sentenceList.add(sentence.toString());
            }
        }
        for(String sentence: sentenceList)
        {
//		String sentence= "Data Structures and Algorithms are one of the most important skills that every computer science student must-have";
            CoreDocument coreDocument= new CoreDocument(sentence);
            stanfordCoreNLP.annotate(coreDocument);
            List<CoreLabel> coreLabelList= coreDocument.tokens();
            for(CoreLabel coreLabel:coreLabelList) {
                // String lemma= coreLabel.lemma();
//				if(t==true) {
//			    topics.add(lemma);
//				}
                String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                partsOfSpeechMap.put(coreLabel.lemma(),pos);
            }
        }
//		for (Entry<String, String> entry : partsOfSpeechMap.entrySet()) {
//	        if (entry.getValue().equals("NN") || entry.getValue().equals("NNS") || entry.getValue().equals("NNP") ) {
//	        	if(field.equalsIgnoreCase("terms")) {
//					terms.add(entry.getKey().toLowerCase());
//				}
//	        	else if(field.equalsIgnoreCase("concepts")){
//	        		concepts.add(entry.getKey().toLowerCase());
//				}
//	        }
////
//	    }
        if(field.equalsIgnoreCase("terms")){
            for (Map.Entry<String, String> entry : partsOfSpeechMap.entrySet()) {
                if (entry.getValue().equals("NN") || entry.getValue().equals("NNS") || entry.getValue().equals("NNP") ) {
                    terms.add(entry.getKey().toLowerCase());
                }

            }
        }
        if(field.equalsIgnoreCase("concepts")){
            for (Map.Entry<String, String> entry : partsOfSpeechMap.entrySet()) {
                if (entry.getValue().equals("NN") || entry.getValue().equals("NNS") || entry.getValue().equals("NNP") ) {
                    concepts.add(entry.getKey().toLowerCase());
                }

            }
        }
    }

    public ArrayList<String> getConcepts(){
        return this.concepts;
    }
    public ArrayList<String> getTerms(){
        return this.terms;
    }
}
