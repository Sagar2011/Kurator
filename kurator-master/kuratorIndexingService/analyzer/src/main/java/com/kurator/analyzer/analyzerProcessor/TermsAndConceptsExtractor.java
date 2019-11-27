package com.kurator.analyzer.analyzerProcessor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TermsAndConceptsExtractor {

    ArrayList<String> commonTerms = new ArrayList<>();
    ArrayList<String> commonConcepts= new ArrayList<>();
    ArrayList<String> uniqueTerms = new ArrayList<>();
    public ArrayList<String> ExtractCommonTerms(ArrayList<String> termsInDoc, ArrayList<String> termsInSemanticDB){
        for(String term:termsInDoc) {
            if(termsInSemanticDB.contains(term)) {
                commonTerms.add(term);
            }
        }
        for(String term:commonTerms){
            if(!uniqueTerms.contains(term)){
                uniqueTerms.add(term);
            }
        }
        if(uniqueTerms.size() < 5 ){
            System.out.println(commonTerms.size()+" is the size of intersection terms");
            return termsInDoc;
        }
        return commonTerms;
    }

    public ArrayList<String> ExtractCommonConcepts(ArrayList<String> conceptsInDoc, ArrayList<String> conceptsInSemanticDB){
        for(String concept:conceptsInDoc) {
            if(conceptsInSemanticDB.contains(concept)) {
                commonConcepts.add(concept);
            }
        }
        return commonConcepts;
    }
}
