package com.kurator.semanticService.model;

import java.util.ArrayList;
import java.util.HashMap;

public class IntentMapping {

    HashMap<String, ArrayList<String>> intentRelatedToTerms = new HashMap<>();
    HashMap<String, ArrayList<String>> intentRelatedToCounterTerms = new HashMap<>();

    public IntentMapping(HashMap<String, ArrayList<String>> intentRelatedToTerms, HashMap<String, ArrayList<String>> intentRelatedToCounterTerms) {
        this.intentRelatedToTerms = intentRelatedToTerms;
        this.intentRelatedToCounterTerms = intentRelatedToCounterTerms;
    }

    public IntentMapping() {
    }

    public HashMap<String, ArrayList<String>> getIntentRelatedToTerms() {
        return intentRelatedToTerms;
    }

    public void setIntentRelatedToTerms(HashMap<String, ArrayList<String>> intentRelatedToTerms) {
        this.intentRelatedToTerms = intentRelatedToTerms;
    }

    public HashMap<String, ArrayList<String>> getIntentRelatedToCounterTerms() {
        return intentRelatedToCounterTerms;
    }

    public void setIntentRelatedToCounterTerms(HashMap<String, ArrayList<String>> intentRelatedToCounterTerms) {
        this.intentRelatedToCounterTerms = intentRelatedToCounterTerms;
    }

}
