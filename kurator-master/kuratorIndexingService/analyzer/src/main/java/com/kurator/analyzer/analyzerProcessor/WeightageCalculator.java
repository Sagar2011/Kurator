package com.kurator.analyzer.analyzerProcessor;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class WeightageCalculator {
    HashMap<String,Integer> termsMap=new HashMap<>();
    String term;
    int weightage;
    String modifiedTerm;
    public HashMap<String,Integer> calculateWeightage(ArrayList<String> intersectionTerms, String title, ArrayList<String> head,
                                                      ArrayList<String> h1, ArrayList<String> h2, ArrayList<String> h3,
                                                      ArrayList<String> h4, ArrayList<String> h5, ArrayList<String> h6)
    {
        for(String term:intersectionTerms){
            modifiedTerm= term.replace(".","");
            this.term=modifiedTerm;
            this.weightage=1;
            if(title.toLowerCase().contains(modifiedTerm)){
                weightage=  weightage+3;
            }
            if(head.contains(term)){
                weightage=  weightage+2;
            }
            if(h1.contains(term)){
                weightage=  weightage+2;
            }
            if(h2.contains(term)){
                weightage= weightage+1;
            }
            if(h3.contains(term)){
                weightage=  weightage+1;
            }
            if(h4.contains(term)){
                weightage=  weightage+1;
            }
            if(h5.contains(term)){
                weightage=  weightage+1;
            }
            if(h6.contains(term)){
                weightage=  weightage+1;
            }
//            weightage=  Math.round(weightage * 100) / 100;
            termsMap.put(modifiedTerm,weightage);
        }
        return termsMap;
    }
}
