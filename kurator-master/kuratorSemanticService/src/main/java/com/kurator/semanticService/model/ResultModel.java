package com.kurator.semanticService.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class ResultModel extends Document {

    private HashSet<String> topicToDocument;
    private ArrayList<HashMap<?, ?>> confidencerating;

    public ResultModel(String documentId, String title, String description, String url, String addedBy, String addedOn, Visibility visibility) {
        super(documentId, title, description, url, addedBy, addedOn, visibility);

    }

    public ResultModel(String documentId, String title, String description, String url, String addedBy, String addedOn) {
        super(documentId, title, description, url, addedBy, addedOn);
    }

    public ResultModel() {

    }

    public HashSet<String> getTopicToDocument() {
        return topicToDocument;
    }

    public void setTopicToDocument(HashSet<String> topicToDocument) {
        this.topicToDocument = topicToDocument;
    }

    public ArrayList<HashMap<?, ?>> getConfidencerating() {
        return confidencerating;
    }

    public void setConfidencerating(ArrayList<HashMap<?, ?>> confidencerating) {
        this.confidencerating = confidencerating;
    }
}
